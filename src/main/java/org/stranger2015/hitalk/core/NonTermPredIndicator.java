package org.stranger2015.hitalk.core;

/**
 *
 */
public
class NonTermPredIndicator extends PredicateIndicator{
    /**
     * @param name
     * @param arity
     */
    public
    NonTermPredIndicator ( AtomTerm name, int arity ) {
        super(name, false, arity);
    }

    /**
     * @param name
     * @param rangeTerm
     */
    public
    NonTermPredIndicator ( AtomTerm name, RangeTerm rangeTerm ) {
        super(name, false, rangeTerm);
    }
}
