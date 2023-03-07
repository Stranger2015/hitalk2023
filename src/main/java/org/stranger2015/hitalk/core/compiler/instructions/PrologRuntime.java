package org.stranger2015.hitalk.core.compiler.instructions;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.Term;
import org.stranger2015.hitalk.core.runtime.*;

import java.util.*;
import java.util.stream.IntStream;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.*;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.REF;

/**
 * Contains all the runtime variables and functionalities for a Prolog instantiation.
 *
 * @author Bas Testerink
 */
public
class PrologRuntime {
    private final Deque <Term> localStack;
    private final Deque <Term> trailStack;

    public
    PrologRuntime ( List <Term> heap, Deque <Term> localStack, Deque <Term> trailStack, CodeBase codebase ) {
        this.heap = heap;
        this.localStack = localStack;
        this.trailStack = trailStack;
        this.codebase = codebase;
        reset();
    }

    public
    Deque <Term> getLocalStack () {
        return localStack;
    }

    public
    Deque <Term> getTrailStack () {
        return trailStack;
    }

    public
    enum EMemoryTypes {
        HEAP("heap"),
        REGISTERS("registers"),
        STACK("stack"),
        TRAIL("trail");

        private final String memTypeName;

        /**
         * @param memTypeName
         */
        @Contract(pure = true)
        EMemoryTypes ( String memTypeName ) {
            this.memTypeName = memTypeName;
        }

        /**
         * @return
         */
        @Contract(pure = true)
        public
        String getMemTypeName () {
            return memTypeName;
        }
    }

    private final CodeBase codebase; // Reference to the compiled code
    private final List <Term> heap;//new ArrayList <>();        // Main memory space
    private final List <Term> registers = new ArrayList <>();    // The argument and X registers
    private final List <Term> trail = new ArrayList <>();        // Bound variables
    private final CellAddress h = new CellAddress();
    private final CellAddress hb = new CellAddress();// Points to the top of the heap currently and at last made choicepoint
    private int tr;
    private int b0;                                  // Trail pointer and choice point at moment of current clause call
    private boolean isInWriteMode = false;           // Whether program instructions should write on the heap or not
    private boolean failed = false;                  // Query could not be answered with yes. I.e. computer says ``no".
    private boolean finished = false;                // Whether the engine is finished with a query
    private int s;                                   // Points to arguments of a predicate that is being unified
    private final FrameStack frameStack = new FrameStack(); // Stack of environments and choice points
    private CodeClause p_clause = null;
    private CodeClause cp_clause = null; // Current code clause
    private int p_index;
    private int cp_index;                // Current index in the clause
    private final List <String> trace = new ArrayList <>();// For the gui, trace of instructions + some other outputs
    private boolean debugMode = false;  // Whether engine is in debug mode.

    /**
     * First resets the runtime environment and then executes the last compiled query.
     *
     * @return True if the query was true and false otherwise.
     */
    public
    boolean execute ( boolean once ) {
        while (isFinished()) {
            Instruction i = p_clause.getInstruction(p_index);
            if (debugMode) {                                        // Some debug code
                StringBuilder r = new StringBuilder();
                if (i instanceof Call) {
                    r.append(": ");
                    String functor = ((Call) i).getFunctor();
                    r.append(functor, 0, functor.indexOf('/')).append("(");
                    CellAddress addr = new CellAddress(REGISTERS.ordinal(), 0, 0);
                    int argcount = Integer.parseInt(functor.substring(functor.indexOf('/') + 1));
                    Map <CellAddress, Integer> vars = new HashMap <>();
                    for (int j = 0; j < argcount; j++) {
                        addr.setIndex(j);
                        cellToPrologString(addr, r, vars);
                        if (j < argcount - 1) {
                            r.append(",");
                        }
                    }
                    r.append(")");
                    if (argcount == 0) {
                        r.setLength(r.length() - 2);    // Remove parenthesis after constants
                    }
                }
                else if (i instanceof CallVariable) {
                    r.append(": ");
                    cellToPrologString(((CallVariable) i).getVariableAddress(this), r, new HashMap <>());
                }
                trace.add("\t%s %s".formatted(i, r));
            }
            i.execute(this);
            if (once) {
                break;
            }
        }

        return !failed;
    }

    /**
     * Resets the runtime environment to answer a new query.
     */
    public
    void reset () {
        failed = false;
        h.set(HEAP.ordinal(), -1, 0);
        hb.set(HEAP.ordinal(), -1, 0);
        s = 0;
        tr = 0;
        b0 = 0;
        isInWriteMode = false;
        finished = false;
        p_index = 0;
        cp_index = 0;
        p_clause = codebase.getQueryClause();
        cp_clause = codebase.getQueryClause();
        frameStack.reset();
        trace.clear();
    }

    /**
     * Creates or reuses a new heap cell. This will automatically scale the heap if all available cells are in use.
     *
     * @return The memory cell from the top of the heap that can be considered a new cell.
     */
    public
    MemoryCell getNewHeapCell () {
        scale(heap, h.getIndex());                // Scale the heap list to the proper size
        MemoryCell result = getCell(h);            // Get the top heap cell
        h.incrementIndex();                        // Increase the top of heap pointer
        //result.reset();						// Reset the cell (not necessary for answering queries but helps for the GUI)
        return result;
    }

    /**
     * Copy the values of a memory cell into an argument or x register. The register will not point to the instance of
     * the memory cell but simply copies all the data fields.
     *
     * @param register
     * @param value
     */
    public
    void setRegister ( int register, MemoryCell value ) {
        scale(registers, register + 1); // Scale the registers list to the proper size
        registers.get(register).copyFrom(value);
    }

    /**
     * Copy the values of a memory cell into a stack variable from the topmost frame on the stack.
     *
     * @param nr    Number of the stack variable.
     * @param value
     */
    public
    void setStackVariable ( int nr, MemoryCell value ) {
        frameStack.setVariable(nr, value);
    }

    /**
     * Obtain the cell from a variable on the top most frame on the stack.
     *
     * @param nr Number of the stack variable.
     * @return
     */
    public
    MemoryCell getStackVariable ( int nr ) {
        return frameStack.getVariable(nr);
    }

    /**
     * Get the cell from an argument/x register.
     *
     * @param register
     * @return
     */
    public
    MemoryCell getRegisterCell ( int register ) {
        scale(registers, register + 1); // Scale the registers list to the proper size
        return registers.get(register);
    }

    /**
     * Follow reference cells until either a self-reference occurs or a non-referencing cell is encountered.
     * To find the target memory and index where the search ended, use <code>getDerefTarget</code> and
     * <code>getDerefPointer</code> right after this method call (or: before the next call to this method).
     *
     * @return The memory cell after dereferencing the input.
     */
    private final CellAddress derefResult = new CellAddress();

    public
    CellAddress deref ( int domain, int frame, int index ) {
        MemoryCell cell = getCell(domain, frame, index);
        if (cell.getType() == REF && !(cell.getPointerDomain() == domain && cell.getPointerFrame() == frame
                &&
                cell.getPointerIndex() == index))
            return deref(cell.getPointerDomain(), cell.getPointerFrame(), cell.getPointerIndex());
        else {
            derefResult.set(domain, frame, index);
            return derefResult;
        }
    }

    /**
     * Binds one variable to another cell.
     * At least one target/pointer pair from the input must point to a reference cell.
     */
    public
    void bind ( CellAddress a1, CellAddress a2 ) {
        MemoryCell c1 = getCell(a1);
        MemoryCell c2 = getCell(a2);
        if (c1.getType() == REF && (c2.getType() != REF || a2.lowerThan(a1))) {
            c1.copyFrom(c2);
            trail(a1);
        }
        else {
            c2.copyFrom(c1);
            trail(a2);
        }
    }

    /**
     * Check whether a cell should be reset if the current choice doesn't pan out.
     *
     * @param a
     */
    public
    void trail ( CellAddress a ) {
        if (a.lowerThan(hb) || (h.lowerThan(a) && a.lowerThan(getB()))) {
            if (trail.size() == tr) {
                trail.add(new CellAddress(a.getDomain(), a.getFrame(), a.getIndex()));
            }
            else {
                trail.get(tr).set(a.getDomain(), a.getFrame(), a.getIndex());
            }
            tr++;
        }
    }

    /**
     * Undo all variable bindings after a certain choicepoint.
     */
    private
    void unwindTrail ( int a, int b ) {
        IntStream.range(a, b).forEachOrdered(i ->
                getCell((CellAddress) trail.get(i)).convertToRefCell((CellAddress) trail.get(i)));
    }

    /**
     * When performing a cut, determine which variables are now bound permanently.
     */
    public
    void tidyTrail () {
        int i = frameStack.peekTopChoicePoint() == null ? 0 : frameStack.peekTopChoicePoint().getTr();
        while (i < tr) {
            if (trail.get(i).lowerThan(hb) ||
                    (h.lowerThan((CellAddress) trail.get(i)) && trail.get(i).lowerThan(getB()))) {
                i++;
            }
            else {
                trail.set(i, trail.get(tr));
                tr--;
            }
        }
    }

    CellAddress a1 = new CellAddress();
    CellAddress a2 = new CellAddress();

    /**
     * Unify two addresses.
     */
    public
    boolean unify ( int d1, int f1, int i1, int d2, int f2, int i2 ) {
        a1.copyFrom(deref(d1, f1, i1));
        a2.copyFrom(deref(d2, f2, i2));
        MemoryCell c1 = getCell(a1.getDomain(), a1.getFrame(), a1.getIndex());
        MemoryCell c2 = getCell(a2.getDomain(), a2.getFrame(), a2.getIndex());
        if (c1.getType() == REF || c2.getType() == REF) {
            bind(a1, a2);
            return true;
        }
        else if (c1.getType() == c2.getType()) {
            switch (c1.getType()) {
                case CON -> {
                    return c1.getFunctor().equals(c2.getFunctor());
                }
                case NUM -> {
                    return c1.getNumber() == c2.getNumber();
                }
                case LIS -> {
                    return unify(
                            c1.getPointerDomain(),
                            c1.getPointerFrame(),
                            c1.getPointerIndex(),
                            c2.getPointerDomain(),
                            c2.getPointerFrame(),
                            c2.getPointerIndex()) &&
                            unify(
                                    c1.getPointerDomain(),
                                    c1.getPointerFrame(),
                                    c1.getPointerIndex() + 1,
                                    c2.getPointerDomain(),
                                    c2.getPointerFrame(),
                                    c2.getPointerIndex() + 1);
                }
                case STR -> {
                    CellAddress fn1 = new CellAddress(
                            c1.getPointerDomain(),
                            c1.getPointerFrame(),
                            c1.getPointerIndex()); // Go to FN cell.
                    c1 = getCell(fn1.getDomain(), fn1.getFrame(), fn1.getIndex());
                    CellAddress fn2 = new CellAddress(c2.getPointerDomain(),
                            c2.getPointerFrame(),
                            c2.getPointerIndex()); // Go to FN cell.
                    c2 = getCell(fn2.getDomain(), fn2.getFrame(), fn2.getIndex());
                    if (c1.getFunctor().equals(c2.getFunctor()) && c1.getArgCount() == c2.getArgCount()) {
                        for (int i = 1; i <= c1.getArgCount(); i++) {
                            if (!unify(fn1.getDomain(), fn1.getFrame(), fn1.getIndex() + i, fn2.getDomain(), fn2.getFrame(), fn2.getIndex() + i)) {
                                return false;
                            }
                        }

                        return true;
                    }
                    else {
                        return false;
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }

    /**
     * Scales a memory space to a target size.
     */
    private
    void scale ( List <Term> list, int targetSize ) {
        for (int i = list.size(); i <= targetSize; i++) {
            MemoryCell m = new MemoryCell();
            m.reset();
            list.add((Term) m);
        }
    }

    /**
     * Create a new environment on the stack.
     *
     * @param permVarCount
     */
    public
    void newEnvironment ( int permVarCount ) {
        frameStack.newEnvironment().convertToEnvironment(cp_index,
                cp_clause,
                permVarCount,
                b0);
    }

    public
    void newChoicePoint ( int arity ) {
        frameStack.newChoicePoint().convertToChoicePoint(
                cp_index,
                cp_clause,
                p_clause,
                getE(),
                tr,
                h.getIndex(),
                b0,
                arity,
                this);
    }

    public
    void setB0AsCurrentChoicePoint () {
        //if(frameStack.getEnvironmentTop()>frameStack.getChoicePointTop())
        frameStack.setChoicePointTop(frameStack.peekTopEnvironment().getB0()); // TODO: I still have to thoroughly test this
        //else frameStack.setChoicePointTop(b0);
    }

    /**
     * Pop the top environment on the stack and restore the values that are stored in it.
     */
    public
    void popEnvironment () {
        Frame f = frameStack.popTopEnvironment();
        p_index = f.getCPIndex();
        p_clause = f.getCPClause();
    }

    /**
     * Pop the top choice point on the stack and restore the values that are stored in it.
     */
    public
    void retryChoicePoint () {
        Frame f = frameStack.peekTopChoicePoint();
        for (int i = 0; i < f.getVariableCount(); i++) {
            setRegister(i, f.getVar(i));
        }
        frameStack.setEnvironmentTop(f.getE());
        cp_clause = f.getCPClause();
        cp_index = f.getCPIndex();
        f.setCurrentClause(p_clause); // replaces STACK[B+n+4] <- L
        unwindTrail(f.getTr(), tr);
        tr = f.getTr();
        h.setIndex(f.getH());
        b0 = f.getB0();
    }

    /**
     * Pop the top frame on the stack and restore the values that are stored in it.
     */
    public
    void trustChoicePoint () {
        Frame f = frameStack.popTopChoicePoint();
        for (int i = 0; i < f.getVariableCount(); i++) {
            setRegister(i, f.getVar(i));
        }
        frameStack.setEnvironmentTop(f.getE());
        cp_clause = f.getCPClause();
        cp_index = f.getCPIndex();
        unwindTrail(f.getTr(), tr);
        tr = f.getTr();
        h.setIndex(f.getH());
        b0 = f.getB0();
    }

    /**
     * Go to the latest choicepoint or fail.
     */
    public
    void backtrack () {
        if (debugMode) {
            trace.add("Backtracking...");
        }
        Frame choicepoint = frameStack.peekTopChoicePoint();
        if (choicepoint == null) {
            fail();
            return;
        }
        p_index = 0;
        p_clause = choicepoint.getCurrentClause().getNext();
        cp_clause = choicepoint.getCPClause();
        cp_index = choicepoint.getCPIndex();
        if (p_clause == null) {
            frameStack.popTopChoicePoint(); // This can only happen after backtracking on something you just retracted.
            backtrack();
        }
    }

    // Getters and setters and related methods
    public
    CodeBase getCodeBase () {
        return codebase;
    }

    public
    List <Term> getHeap () {
        return heap;
    }

    public
    List <Term> getRegisters () {
        return registers;
    }

    public
    CellAddress getH () {
        return h;
    }

    public
    boolean isInWriteMode () {
        return isInWriteMode;
    }

    /**
     * @param b
     * @return
     */
    public
    boolean setWriteMode ( boolean b ) {
        return isInWriteMode = b;
    }

    public
    void fail () {
        failed = true;
    }

    public
    boolean hasFailed () {
        return failed;
    }

//    public
//    int getS () {
//        return s;
//    }

    public
    void setS ( int s ) {
        this.s = s;
    }

    public
    void increaseS () {
        s++;
    }

    public
    void increaseP () {
        p_index++;
    }

    public
    void moveToContinuationInstruction () {
        p_index = cp_index;
        p_clause = cp_clause;
    }

    public
    MemoryCell getHeapCell ( int index ) {
        return heap.get(index);
    }

    public
    boolean isFinished () {
        return !failed && !finished;
    }

    public
    int getE () {
        return frameStack.getEnvironmentTop();
    }

    private static final CellAddress b_address = new CellAddress();

    public
    CellAddress getB () {
        b_address.set(STACK.ordinal(), frameStack.getChoicePointTop(), 0);
        return b_address;
    }

    public
    void setHBtoH () {
        hb.setIndex(h.getIndex());
    }

    public
    void setB0 () {
        b0 = frameStack.getChoicePointTop();
    }

    public
    String getStackString () {
        return frameStack.toString();
    }

    public
    void activateDebugMode () {
        debugMode = true;
    }

    public
    List <String> getTrace () {
        return trace;
    }

    public
    void setCPToNextInstruction () {
        cp_index = p_index + 1;
        cp_clause = p_clause;
    }

    /**
     * Move execution to the first clause of a certain functor.
     */
    public
    void goToClause ( String functor ) {
        p_clause = codebase.getClause(functor);
        b0 = frameStack.getChoicePointTop();
        if (p_clause == null) {
            backtrack();
        }
        else {
            p_index = 0;
        }
    }

    public
    void setFinished ( boolean b ) {
        finished = b;
    }

    /**
     * Obtain a memory cell.
     */
    public
    MemoryCell getCell ( CellAddress address ) {
        return switch (EMemoryTypes.values()[address.getDomain()]) {
            case HEAP -> heap.get(address.getIndex());
            case REGISTERS -> registers.get(address.getIndex());
            case STACK -> frameStack.getFrame(address.getFrame()).getVariables().get(address.getIndex());
            default -> null;
        };
    }

    /**
     * Obtain a memory cell.
     */
    public
    MemoryCell getCell ( int domain, int frame, int index ) {
        return switch (EMemoryTypes.values()[domain]) {
            case HEAP -> heap.get(index);
            case REGISTERS -> registers.get(index);
            case STACK -> frameStack.getFrame(frame).getVariables().get(index);
            default -> null;
        };
    }

    /**
     * Obtain a memory area.
     */
    public
    List <Term> getTarget ( int target ) {

        return switch (EMemoryTypes.values()[target]) {
            case HEAP -> heap;
            case REGISTERS -> registers;
            case STACK -> frameStack.getVariables();
            default -> null;
        };
    }

    // From here on toStrings

    public static
    String targetToString ( int target ) {
        return switch (EMemoryTypes.values()[target]) {
            case HEAP -> "heap";
            case REGISTERS -> "registers";
            case STACK -> "stack";
            case TRAIL -> "trail";
        };
    }

    public
    String toString () {
        StringBuilder r = new StringBuilder();
        r.append("Code base:\r\n").append(codebase);
        r.append("Heap: \r\n");
        int c = 0;
        for (MemoryCell m : heap) {
            if (m.getType() != null) {
                r.append("\t").append(c).append(": ").append(m).append("\r\n");
                c++;
            }
        }
        r.append("Registers:\r\n");
        c = 0;
        for (MemoryCell m : registers) {
            if (m.getType() != null) {
                r.append("\t").append(c).append(": ").append(m).append("\r\n");
                c++;
            }
        }

        return r.toString();
    }

    public
    String getHeapString () {
        StringBuilder r = new StringBuilder();
        r.append("Heap: \r\n");
        int c = 0;
        for (MemoryCell m : heap) {
            if (m.getType() != null) {
                r.append("\t").append(c).append(": ").append(m).append("\r\n");
                c++;
            }
        }

        return r.toString();
    }

    /**
     * @return
     */
    public
    String getRegistersString () {
        StringBuilder r = new StringBuilder();
        r.append("Registers: ").append(registers.size()).append("\r\n");
        int c = 1;
        for (MemoryCell m : registers) {
            r.append("\t").append(c).append(": ").append(m).append("\r\n");
            c++;
        }

        return r.toString();
    }

    /**
     * @return
     */
    public
    String getTraceString () {
        StringBuilder r = new StringBuilder();
        r.append("Trace:\r\n");
        r.append("Next instruction: ").append(p_clause == null ? "none" : p_clause.getInstruction(p_index)).append("\r\n");
        for (int i = trace.size() - 1; i >= 0; i--) {
            r.append(trace.get(i)).append("\r\n");
        }

        return r.toString();
    }

    /**
     * @return
     */
    public
    String getRuntimeVariablesString () {
        StringBuilder r = new StringBuilder();
        r.append("Runtime variables:\r\n");
        r.append("h:\t\t").append(h.getIndex()).append("\r\n");
        r.append("hb:\t\t").append(hb.getIndex()).append("\r\n");
        r.append("tr:\t\t").append(tr).append("\r\n");
        r.append("b0:\t\t").append(b0).append("\r\n");
        r.append("write:\t").append(isInWriteMode).append("\r\n");
        r.append("failed:\t").append(failed).append("\r\n");
        r.append("s:\t\t").append(s).append("\r\n");
        String c = p_clause == null ? "null" : (p_clause.belongsTo == null ? "query" : p_clause.belongsTo);
        r.append("p:\t\t").append(p_index).append(" (clause from: ").append(c).append(")\r\n");
        c = cp_clause.belongsTo == null ? "query" : cp_clause.belongsTo;
        r.append("cp:\t\t").append(cp_index).append(" (clause from: ").append(c).append(")\r\n");

        return r.toString();
    }

    /**
     * @param a
     * @param r
     * @param vars
     * @return
     */
    public
    StringBuilder cellToPrologString ( CellAddress a, StringBuilder r, Map <CellAddress, Integer> vars ) {
        CellAddress d = deref(a.getDomain(), a.getFrame(), a.getIndex());
        CellAddress addr = new CellAddress(d.getDomain(), d.getFrame(), d.getIndex());
        MemoryCell m = getCell(addr);
        switch (m.getType()) {
            case STR -> {
                addr = new CellAddress(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex());
                m = getCell(addr);
                r.append(m.getFunctor()).append("(");
                for (int i = 1; i <= m.getArgCount(); i++) {
                    addr.setIndex(addr.getIndex() + 1);
                    cellToPrologString(addr, r, vars);
                    if (i < m.getArgCount()) {
                        r.append(",");
                    }
                }
                r.append(")");
            }
            case LIS -> {
                CellAddress child1 = new CellAddress(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex());
                CellAddress child2 = new CellAddress(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex() + 1);
                r.append("[");
                cellToPrologString(child1, r, vars);
                r.append("|");
                cellToPrologString(child2, r, vars);
                r.append("]");
            }
            case REF -> {
                int nr = vars.size();
                for (CellAddress a2 : vars.keySet()) {
                    if (a2.equals(addr)) {
                        nr = vars.get(a2);
                        break;
                    }
                }
                if (nr == vars.size()) {
                    vars.put(new CellAddress(addr.getDomain(), addr.getFrame(), addr.getIndex()), nr);
                }
                r.append("_V").append(nr);
            }
            case CON -> r.append(m.getFunctor());
            case NUM -> r.append(m.getNumber());
        }

        return r;
    }

    /**
     * @param qvars
     * @return
     */
    public
    Map <String, String> outputQueryResult ( Map <String, Integer> qvars ) {
        Map <String, String> bindings = new HashMap <String, String>();
        StringBuilder r = new StringBuilder();
        Map <CellAddress, Integer> vars = new HashMap <CellAddress, Integer>();
        for (String s : qvars.keySet()) {
            bindings.put(s, cellToPrologString(new CellAddress(STACK.ordinal(), 0, qvars.get(s)), r, vars).toString());
            r = new StringBuilder();
        }
        //for(String s : bindings.keySet()) System.out.println(s+" = "+bindings.get(s));
        return bindings;
    }
}