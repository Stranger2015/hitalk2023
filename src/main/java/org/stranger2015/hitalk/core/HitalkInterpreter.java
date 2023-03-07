package org.stranger2015.hitalk.core;

import javax.naming.Context;
import java.util.*;
import java.util.function.Function;

import static org.stranger2015.hitalk.core.HitalkInterpreter.BodyState.*;
import static org.stranger2015.hitalk.core.ListTerm.*;
import static org.stranger2015.hitalk.core.Term.VAR;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.CON;

/**
 *
 */
public
class HitalkInterpreter {
    private BodyState state;
    private PredicateDefinition pdef;

    public
    enum BodyState {
        CALL,
        LAST_CALL,
        EXIT,
        REDO,
        FAIL
    }

    public static final Deque <Term> unifyStack1 = new ArrayDeque <>();
    public static final Deque <Term> unifyStack2 = new ArrayDeque <>();

    private static final String BUILT_IN = "built_in";
    private static final String LIBRARY = "library";
    private static final String STATIC = "static";
    private static final String DYNAMIC = "dynamic";

    private AtomTerm qualifiedName;
    private Term arg2;
    private final CompoundTerm colonColon1 = new CompoundTerm(qualifiedName, AtomTerm.createAtom("::"), arg2);

    private
    AtomTerm createAtom ( String s ) {
        return new AtomTerm(CON);
    }

    private final CompoundTerm colon = new CompoundTerm(qualifiedName, AtomTerm.createAtom(":"), arg2);
    private final CompoundTerm upUp = new CompoundTerm(qualifiedName, AtomTerm.createAtom("^^"), arg2);

    public static final Map <String, AtomTerm> atomTable = new HashMap <>();
    private final Map <PredicateIndicator, Entity> entityTable = new HashMap <>();
    private final Function <PredicateIndicator, Boolean> code;
    private final Map <PredicateIndicator, Predicate> predicateTable = new HashMap <>();

    /**
     * @param code
     */
    public
    HitalkInterpreter ( Function <PredicateIndicator, Boolean> code ) {
        this.code = code;
    }

    /**
     * @param goal
     * @return
     */
    public
    void solve ( Deque <Context> ctxStack, PredicateIndicator entityId, ListTerm goal ) {
        state = CALL;
        boolean loop=true;
        while (loop) {
            switch (state) {
                case CALL -> {
                   call(ctxStack, entityId, goal);

                }
                case LAST_CALL -> {
                   lastCall(ctxStack, entityId, goal);
                }
                case REDO -> {
                   redo(ctxStack, entityId, goal);
                }
                case EXIT -> {
                   exit(ctxStack, entityId, goal);
                }

                case FAIL -> {
                   fail(ctxStack, entityId, goal);
                   loop=false;
                }
                default -> throw new IllegalStateException("Unexpected value: " + state);
            }
        }
    }

    private
    void call ( Deque <Context> ctxStack, PredicateIndicator entityId, CompoundTerm goal ) {
        Entity entity = entityTable.get(entityId);
        PredicateIndicator predicateIndicator = goal.toPredicateIndicator(true);
        /*PredicateDefinition */pdef = (PredicateDefinition) entity.getPredicateDeclTable().get(predicateIndicator);
        List <Clause> code = pdef.getCode();
        Predicate predicate = (Predicate) entity.getPredicate(predicateIndicator);
        IProperty property = predicate.getProperty(BUILT_IN);
        if (property == null) {
            Clause clause=code.get(0);
            List <Clause> clauses = code.subList(1, code.size());
            if (unify(clause.getHead(), goal)) {
                ListTerm body = (ListTerm) clause.getBody();
                int len = body.getLength();
                if (len == 0) {
                    state = EXIT;
                }
                else if (len == 1) {
                    state = LAST_CALL;
                } else {
                    
                }
            }else {
                state=REDO;
            }
        }
    }
    private
    void  redo ( Deque <Context> ctxStack, PredicateIndicator entityId, CompoundTerm goal ) {
        
    }
    private
    void lastCall ( Deque <Context> ctxStack, PredicateIndicator entityId, CompoundTerm goal ) {

    }
    private
    void fail ( Deque <Context> ctxStack, PredicateIndicator entityId, CompoundTerm goal ) {

    }
    private
    void exit ( Deque <Context> ctxStack, PredicateIndicator entityId, CompoundTerm goal ) {

    }

    /**
     * function unify(ix, iy) {
     * initialize stack Sx
     * initialize stack Sy
     * push ix on stack Sx
     * push iy on stack Sy
     * while (not_empty(Sx) and not_empty(Sy)) {// start main loop
     * pop i from Sx
     * pop j from Sy
     * // case 1: i is bound to a term and j is bound to a term}
     * if (type(i) == STR and type(j) == STR)
     * if (main functors of i and j match (both name and arity))
     * if (arity > 0)
     * push components of i on Sx in sequence
     * push components of j on Sy in sequence
     * else
     * return(FAIL) // report failure
     * // case 2: i is bound to a term and j is bound to a variable
     * if (type(i) == STR and type(j) == VAR)
     * if (j is a free variable)
     * bind j to i and set mgu[j] = 1
     * else // j is bound
     * dereference j
     * if (j is bound to a STR)
     * push i on Sx
     * push j on Sy
     * else // j is bound to a free variable
     * bind j to i
     * // case 3: i is bound to a variable and j is bound to a term
     * if (type(i) == VAR and type(j) == STR)
     * // perfectly symmetric to case 2
     * // case 4: i is bound to a variable and j is bound to a variable
     * if (type(i) == VAR and type(j) == VAR)
     * if (i is free and j is free)
     * bind i to j (or vice versa) and set mgu[i] = 1
     * if (i is free and j is bound)
     * bind i to j and set mgu[i] = 1
     * if (i is bound and j is free)
     * bind j to i and set mgu[j] = 1
     * if (i is bound and j is bound)
     * push the index of the term to which i is bound on Sx
     * push the index of the term to which j is bound on Sy
     * } // end main loop
     * return(SUCCESS)
     * } // end function unify
     *
     * @param term1
     * @param term2
     */
    private static
    boolean unify ( Term term1, Term term2 ) {
        unifyStack1.push(term1);
        unifyStack2.push(term2);
        while (!unifyStack1.isEmpty() && !unifyStack2.isEmpty()) {
            term1 = unifyStack1.pop();
            term2 = unifyStack2.pop();
            if (term1.getKind() == term2.getKind()) {
                if (term1.isCompound()) {
                    if (((CompoundTerm) term1).getName() == ((CompoundTerm) term2).getName()
                            &&
                            ((CompoundTerm) term1).getArity() == ((CompoundTerm) term2).getArity()
                    ) {
                        Term args1 = ((CompoundTerm) term1).getArgs();
                        pushArgs(unifyStack1, args1);
                        Term args2 = ((CompoundTerm) term2).getArgs();
                        pushArgs(unifyStack2, args2);
                    }
                }
                else {
                    return false;
                }
                if (term1.isCompound() && term2.isVar()) {
                    if (term2.isFree()) {
                        term1.value = (ListTerm) term2;
                    }
                }
                else {
                    deref((ListTerm) term2);
                    if (term2.value.isCompound()) {
                        unifyStack1.push(term1);
                        unifyStack2.push(term2);
                    }
                    else {
                        term2.value = (ListTerm) term1;//bind i to j
                    }
                    if (term1.isVar() && term2.isCompound()) {
                        term1.value = (ListTerm) term2;
                    }
                    else if (term1.isFree()) {
                        term1.value = (ListTerm) term2;
                    }
                    else if (term2.isFree()) {
                        term2.value = (ListTerm) term1;
                    }
                    else {
                        unifyStack1.push(term1.value);
                        unifyStack2.push(term2.value);
                    }
                }
            }
        }

        return true;
    }

    /**
     * @param term
     */
    private static
    void deref ( ListTerm term ) {
        while (term.isVar()) {
            if (term.isFree()) {
                break;
            }
            term.value = term;
        }

    }

    private static
    void pushArgs ( Deque <Term> unifyStack, Term args ) {
        while (true) {
            if (args == EMPTY_LIST) {
                return;
            }
            if (args.getKind() == VAR) {
                unifyStack.push(args);
            }
            ListTerm list = (ListTerm) args;
            unifyStack.push(list.getHead());
            args = list.getTail();
        }
    }

    /**
     * @return
     */
    public
    Map <PredicateIndicator, Entity> getEntityTable () {
        return entityTable;
    }

    /**
     * @return
     */
    public
    Map <PredicateIndicator, Predicate> getPredicateTable () {
        return predicateTable;
    }

    /**
     * @return
     */
    public
    Map <String, AtomTerm> getAtomTable () {
        return atomTable;
    }

    /**
     * @return
     */
    public
    Function <PredicateIndicator, Boolean> getCode () {
        return code;
    }
}