package org.stranger2015.hitalk.core.compiler;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.AtomTerm;
import org.stranger2015.hitalk.core.ListTerm;
import org.stranger2015.hitalk.core.Term;

import java.util.ArrayList;
import java.util.List;

import static org.stranger2015.hitalk.core.AtomTerm.BYPASS;

/**
 *
 */
public
class TermExpander implements ITransformer {
    protected final
    List <Term> termsToExpand = new ArrayList <>();

    /**
     *
     */
    public
    TermExpander () {

    }

    /**
     * default exp {x}
     *
     * @param term
     * @return
     */
    public
    List<Term> expand ( Term term ) {
        List<Term> result = List.of();
        if (term.isAtom() && term == BYPASS) {
            termsToExpand.remove(term);//fixme

            result = List.of(term);
        } else{
            termsToExpand.addAll(call(AtomTerm.createAtom("term_expansion")).toList());
        }

        return result;
    }

    /**
     * @param atomTerm
     * @return
     */
    public
    ListTerm call ( AtomTerm atomTerm ) {
        return new ListTerm(atomTerm);
    }


    /**
     *
     */
    @Override
    public
    void transform () {
        expand(termsToExpand.get(0));
    }

    /**
     * @param term
     */
    @Contract(pure = true)
    private
    void push ( Term term ) {
        termsToExpand.add(term);
    }

    /**
     * @return
     */
    private
    Term pop () {
        return termsToExpand.remove(0);
    }
}
