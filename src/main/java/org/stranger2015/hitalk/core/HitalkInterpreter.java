package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.HitalkRuntime;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.CON;

/**
 *
 */
public
class HitalkInterpreter extends LogtalkInterpreter {
    private static AtomTerm qualifiedName;
    private static Term arg2;
    protected final Map <PredicateIndicator, Entity <?>> entityTable = new HashMap <>();

    @Override
    protected
    Entity <?> getEntity () {
        return super.getEntity();
    }
private final
    Map <PredicateIndicator, Predicate> predicateTable = new HashMap <>();

    private
    AtomTerm createAtom ( String s ) {
        return new AtomTerm(CON.ordinal());
    }

    private static final CompoundTerm colonColon = new CompoundTerm(qualifiedName, AtomTerm.createAtom("::"), arg2);
    private static final CompoundTerm colon = new CompoundTerm(qualifiedName, AtomTerm.createAtom(":"), arg2);
    private static final CompoundTerm upUp = new CompoundTerm(qualifiedName, AtomTerm.createAtom("^^"), arg2);
    private static final CompoundTerm hash = new CompoundTerm(qualifiedName, AtomTerm.createAtom("#"), arg2);
    private static final CompoundTerm hashHash = new CompoundTerm(qualifiedName, AtomTerm.createAtom("##"), arg2);
    private static final CompoundTerm cactus = new CompoundTerm(qualifiedName, AtomTerm.createAtom(">>"), arg2);
    private static final CompoundTerm linear = new CompoundTerm(qualifiedName, AtomTerm.createAtom(">>>"), arg2);

    /**
     * solve(_, true).
     * solve(Ctx, (A1, A2)).
     * solve(Ctx, B).
     *
     * @param heap
     * @param localStack
     * @param trailStack
     * @param codebase
     * @param code
     */
    public
    HitalkInterpreter (
            HitalkRuntime runtime,
            List <Term> heap,
            Deque <Term> localStack,
            Deque <Term> trailStack,
            CodeBase codebase,
            Function <PredicateIndicator, Boolean> code ) {

        super(runtime, heap, localStack, trailStack, codebase, code);

        init();
    }

    private
    void init () {
        createObject(AtomTerm.createAtom("user"));
        createObject(AtomTerm.createAtom("logtalk"));
        createObject(AtomTerm.createAtom("hitalk"));
    }

    private
    void createObject ( AtomTerm user ) {

    }
//    /**
//     * function unify(ix, iy) {

//     * initialize stack pdl
//     * push ix on stack pdl
//     * push iy on stack pdl

//     * while (not_empty(pdl)) {// start main loop
//     * pop i from pdl
//     * pop j from pdl
//     * // case 1: i is bound to a term and j is bound to a term}
//     * if (type(i) == STR and type(j) == STR)
//     * if (main functors of i and j match (both name and arity))
//     * if (arity > 0)
//     * push components of i on pdl in sequence
//     * push components of j on pdl in sequence
//     * else
//     * return(FAIL) // report failure
//     * // case 2: i is bound to a term and j is bound to a variable
//     * if (type(i) == STR and type(j) == VAR)
//     * if (j is a free variable)
//     * bind j to i and set mgu[j] = 1
//     * else // j is bound
//     * dereference j
//     * if (j is bound to a STR)
//     * push i on pdl
//     * push j on pdl
//     * else // j is bound to a free variable
//     * bind j to i
//     * // case 3: i is bound to a variable and j is bound to a term
//     * if (type(i) == VAR and type(j) == STR)
//     * // perfectly symmetric to case 2
//     * // case 4: i is bound to a variable and j is bound to a variable
//     * if (type(i) == VAR and type(j) == VAR)
//     * if (i is free and j is free)
//     * bind i to j (or vice versa) and set mgu[i] = 1
//     * if (i is free and j is bound)
//     * bind i to j and set mgu[i] = 1
//     * if (i is bound and j is free)
//     * bind j to i and set mgu[j] = 1
//     * if (i is bound and j is bound)
//     * push the index of the term to which i is bound on Sx
//     * push the index of the term to which j is bound on Sy
//     * } // end main loop
//     * return(SUCCESS)
//     * } // end function unify
//     *
//     * @param term1
//     * @param term2
//     */
//    public static
//    boolean unify ( Term term1, Term term2 ) {
//        pdl.push(term1);
//        pdl.push(term2);
//        while (!pdl.isEmpty()) {
//            term1 = pdl.pop();
//            term2 = pdl.pop();
//            if (term1.getKind() == term2.getKind()) {
//                if (term1.isCompound()) {
//                    if (((CompoundTerm) term1).getName() == ((CompoundTerm) term2).getName()
//                            &&
//                            ((CompoundTerm) term1).getArity() == ((CompoundTerm) term2).getArity()
//                    ) {
//                        Term args1 = ((CompoundTerm) term1).getArgs();
//                        pushArgs(pdl, args1);
//                        Term args2 = ((CompoundTerm) term2).getArgs();
//                        pushArgs(pdl, args2);
//                    }
//                }
//                else {
//                    return false;
//                }
//                if (term1.isCompound() && term2.isVar()) {
//                    if (term2.isFree()) {
//                        term1.value = term2;
//                    }
//                }
//                else {
//                    deref((ListTerm) term2);
//                    if (term2.value.isCompound()) {
//                        pdl.push(term1);
//                        pdl.push(term2);
//                    }
//                    else {
//                        term2.value = term1;//bind i to j
//                    }
//                    if (term1.isVar() && term2.isCompound()) {
//                        term1.value = term2;
//                    }
//                    else if (term1.isFree()) {
//                        term1.value = term2;
//                    }
//                    else if (term2.isFree()) {
//                        term2.value = term1;
//                    }
//                    else {
//                        pdl.push(term1.value);
//                        pdl.push(term2.value);
//                    }
//                }
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * @param term
//     */
//    private static
//    void deref ( ListTerm term ) {
//        while (term.isVar()) {
//            if (term.isFree()) {
//                break;
//            }
//            term.value = term;
//        }
//    }
//
//    private static
//    void pushArgs ( Deque <Term> pdl, Term args ) {
//        while (true) {
//            if (args == EMPTY_LIST) {
//                return;
//            }
//            if (args.getKind() == VAR) {
//                unifyStack.push(args);
//            }
//            ListTerm list = (ListTerm) args;
//            pdl.push(list.getHead());
//            args = list.getTail();
//        }
//    }

    /**
     * //     * @return
     * //
     */
    public
    Map <PredicateIndicator, Entity<?>> getEntityTable () {
        return entityTable;
    }

    /**
     * @return
     */
    public
    Map <PredicateIndicator, Predicate> getPredicateTable () {
        return predicateTable;
    }


}