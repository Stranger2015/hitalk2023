package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import java.util.*;
import java.util.function.Function;

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
    private final List <Term> heap;
    private final Deque <Term> localStack;
    private final Deque <Term> trailStack;
    private final CodeBase codebase;
    private final Entity entity;

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
    private static final String BUILT_IN = "built_in";
    private static final String LIBRARY = "library";
    private static final String STATIC = "static";
    private static final String DYNAMIC = "dynamic";

    /**
     * @param runtime
     * @param heap
     * @param localStack
     * @param trailStack
     * @param codebase
     * @param code
     * @param entity
     */
    public
    PrologInterpreter ( PrologRuntime runtime,
                        List <Term> heap,
                        Deque <Term> localStack,
                        Deque <Term> trailStack,
                        CodeBase codebase,
                        Function <PredicateIndicator, Boolean> code, Entity entity ) {

        this.runtime = runtime;
        this.heap = heap;
        this.localStack = localStack;
        this.trailStack = trailStack;
        this.codebase = codebase;
        this.entity = entity;
//        this.code = code;
        runtime.reset();
    }

    public
    List <Term> getHeap () {
        return heap;
    }

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

    /**
     * @return
     */
    public
    Function <PredicateIndicator, Boolean> getCode () {
        return code;
    }

    private final Term query = ListTerm.EMPTY_LIST;

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

    private
    void call () {
        // Entity entity = entityTable.get(entityId);
        CompoundTerm goal = null;
        PredicateIndicator predicateIndicator = goal.toPredicateIndicator(true);
        pdef = (PredicateDefinition) entity.getPredicateDeclTable().get(predicateIndicator);
        List <Clause> code = pdef.getCode();
        Predicate predicate = (Predicate) entity.getPredicate(predicateIndicator);
        IProperty property = predicate.getProperty(BUILT_IN);
        if (property == null) {
            Clause clause = code.get(0);
            List <Clause> clauses = code.subList(1, code.size());
            if (unify(clause.getHead(), goal)) {
                ListTerm body = (ListTerm) clause.getBody();
                RangeTerm len = body.getLength();
                if (len == 0) {
                    state = EXIT;
                }
                else if (len == 1) {
                    state = LAST_CALL;
                }
                else {

                }
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
    Entity getEntity () {
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