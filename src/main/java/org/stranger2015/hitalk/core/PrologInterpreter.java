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
    public static final AtomTerm TRUE = AtomTerm.createAtom("true");
    public static final AtomTerm USER = AtomTerm.createAtom("user");
    /**
     *
     */
    protected PrologRuntime runtime;
    private final List <Term> heap;
    private final Deque <Term> localStack;
    private final Deque <Term> trailStack;
    private final CodeBase codebase;
    private final Function <PredicateIndicator, Boolean> code;

    public static final Map <String, AtomTerm> atomTable = new HashMap <>();
        protected final Map <PredicateIndicator, Entity> entityTable = new HashMap <>();
    protected final Map <PredicateIndicator, Predicate> predicateTable = new HashMap <>();
    protected Entity entity = new PrologModule((byte) 0, new PredicateIndicator(USER, 0), null);

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
        this.code = code;

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
        pdef = (PredicateDefinition) getEntity().getPredicateDeclTable().get(predicateIndicator);
        List <Clause> code = pdef.getCode();
        Predicate predicate = (Predicate) entity.getPredicate(predicateIndicator);
        IProperty property = predicate.getProperty(BUILT_IN);
        if (property == null) {
            Clause clause = code.get(0);
            List <Clause> clauses = code.subList(1, code.size());
            if (unify(clause.getHead(), goal)) {
                ListTerm body = (ListTerm) clause.getBody();
                int len = body.getLength();
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