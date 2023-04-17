package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.FrameStack;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import java.util.*;
import java.util.function.Function;

import static org.stranger2015.hitalk.core.ListTerm.*;
import static org.stranger2015.hitalk.core.PrologInterpreter.BodyState.*;

/**
 *
 */
public
class PrologInterpreter {
    /**
     *
     */
    protected final PrologRuntime runtime;
    protected final List <Term> heap;
    protected final Deque <Term> localStack;
    protected final Deque <Term> trailStack;
    protected final CodeBase codebase;
//    protected final Entity<?> entity;
//    protected final Map <PredicateIndicator, DirectiveProc<?>> procTable;

    public
    PrologInterpreter ( PrologRuntime runtime,
                        List <Term> heap,
                        Deque <Term> localStack,
                        Deque <Term> trailStack,
                        CodeBase codebase,
                        Function <PredicateIndicator, Boolean> code ) {

        this.runtime = runtime;
        this.heap = heap;
        this.localStack = localStack;
        this.trailStack = trailStack;
        this.codebase = codebase;
    }

    //    protected final Map <String, AtomTerm> atomTable=new HashMap<>();
//    @Contract(pure = true)
//    protected final
//    Map <PredicateIndicator, DirectiveProc<?>> getProcTable () {
//        return procTable;
//    }

    /**
     *
     */
    public
    enum BodyState {
        CALL,
        EXIT,
        FAIL,
        LAST_CALL,
        REDO
    }

    public static final Deque <Term> pdl = new ArrayDeque <>();
    protected static final String BUILT_IN = "built_in";
    protected static final String LIBRARY = "library";
    protected static final String STATIC = "static";
    protected static final String DYNAMIC = "dynamic";

    /**
     * @param runtime
     * @param heap
     * @param localStack
     * @param trailStack
     * @param codebase
     * @param entity
     */
    public
    PrologInterpreter ( PrologRuntime runtime,
                        List <Term> heap,
                        Deque <Term> localStack,
                        Deque <Term> trailStack,
                        CodeBase codebase
/*//                        Map<PredicateIndicator, DirectiveProc<?>> procTable ,
                        Entity<?> entity*/ ) {

        this.runtime = runtime;
        this.heap = heap;
        this.localStack = localStack;
        this.trailStack = trailStack;
        this.codebase = codebase;
//        this.entity = entity;
//        this.procTable = procTable ;
        runtime.reset();
    }

    public
    List <Term> getHeap () {
        return heap;
    }

    /**
     * @return
     */
    public
    Deque <Term> getLocalStack () {
        return localStack;
    }

    public
    Deque <Term> getTrailStack () {
        return trailStack;
    }

    /**
     * @return
     */
    public
    CodeBase getCodebase () {
        return codebase;
    }

    protected final Term query = EMPTY_LIST;

    /**
     * @return
     */
    public
    void solve () {
        state = CALL;
        boolean loop = true;
        while (loop) {
            switch (state) {
                case CALL -> {
                    call();
                }
                case LAST_CALL -> {
                    lastCall();
                }
                case REDO -> {
                    redo();
                }
                case EXIT -> {
                    exit();
                }
                case FAIL -> {
                    fail();
                    loop = false;
                }
                default -> throw new IllegalStateException("Unexpected value: %s".formatted(state));
            }
        }
    }

    public boolean call(CompoundTerm goal){
//        FrameStack

              return false;
    }

    private
    void call () {
        // Entity entity = entityTable.get(entityId);
        CompoundTerm goal = null;//fixme
        PredicateIndicator predicateIndicator = goal.toPredicateIndicator();
//        pdef = entity.getPredicateDeclTable().get(predicateIndicator);
        var code = pdef.getCode();
        Predicate predicate = entity.getPredicate(predicateIndicator);
        IProperty property = predicate.getProperty(BUILT_IN);
        if (property == null) {
            Clause clause = code.get(0);
            List <Clause> clauses = code.subList(1, code.size());
            if (unify(clause.getHead(), goal)) {
                ListTerm body = (ListTerm) clause.getBody();
                RangeTerm len = body.getLength();
                int arityLow=len.getArityLow();
                int arityHigh= len.getArityHigh();

//                if (len == 0) {
//                    state = EXIT;
//                }
//                else if (len == 1) {
//                    state = LAST_CALL;
//                }
//                else {
//
//                }
            }
            else {
                state = REDO;
            }
        }
    }

    private
    boolean unify ( Term head, Term goal ) {
        return false;
    }

    protected
    Entity<?> getEntity () {
        return entity;
    }

    private
    void redo () {

    }

    private
    void lastCall () {

    }

    private
    void fail () {

    }

    private
    void exit () {

    }

    private BodyState state;
    /**
     *
     */
    private PredicateDefinition pdef;
}