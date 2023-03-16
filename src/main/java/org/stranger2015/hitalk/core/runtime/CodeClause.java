package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.compiler.instructions.Fail;
import org.stranger2015.hitalk.core.compiler.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public
class CodeClause {
    private CodeClause next = null;
    private CodeClause previous = null;
    private boolean retracted = false;
    private List <Instruction> instructions = new ArrayList <>();
    private static final Instruction fail = new Fail();        // Special fail instruction when a predicate cannot be found
    private String prologString = "";

    /**
     *
     */
    public
    void reset () {
        next = null;
        retracted = false;
        instructions.clear();
        previous = null;
    }

    /**
     * @param next
     */
    public
    void setNext ( CodeClause next ) {
        this.next = next;
    }

    /**
     * @param previous
     */
    public
    void setPrevious ( CodeClause previous ) {
        this.previous = previous;
    }

    /**
     * @return
     */
    public
    CodeClause getNext () {
        if (next == null) {
            return null;
        }
        if (next.isRetracted()) {
            return next.getNext();
        }

        return next;
    }

    /**
     * @return
     */
    public
    CodeClause getPrevious () {
        if (previous == null) {
            return null;
        }
        if (previous.isRetracted()) {
            return previous.getPrevious();
        }

        return previous;
    }

    /**
     * @param b
     */
    public
    void setRetracted ( boolean b ) {
        this.retracted = b;
    }

    /**
     * @return
     */
    public
    boolean isRetracted () {
        return retracted;
    }

    /**
     * @param instructions
     */
    public
    void setInstructions ( List <Instruction> instructions ) {
        this.instructions = instructions;
    }

    /**
     * @return
     */
    public
    List <Instruction> getInstructions () {
        return instructions;
    }

    /**
     * @param index
     * @return
     */
    public
    Instruction getInstruction ( int index ) {
        return index >= instructions.size() ? fail : instructions.get(index);
    }

    /**
     * @param s
     */
    public
    void setPrologString ( String s ) {
        prologString = s;
    }

    /**
     * @return
     */
    public
    String getPrologString () {
        return prologString;
    }

    /**
     * @return
     */
    public
    int size () {
        return instructions.size();
    }

    /**
     *
     */
    public
    void remove () {
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
        reset();
    }

    public String belongsTo; // for debugging

    /**
     * @return
     */
    public
    String toString () {
        int c = 0;
        StringBuilder s = new StringBuilder("\t %s\r\n".formatted(prologString));
        for (Instruction i : instructions) {
            s.append("\t %d: %s\r\n".formatted(c, i));
            c++;
        }
        return s.toString();
    }
}
