package org.stranger2015.hitalk.core;

/**
 *
 */
public
class NonTermPredIndicator extends PredicateIndicator {

    public static final AtomTerm DOUBLE_SLASH = AtomTerm.createAtom("//");

    /**
     * @param name
     * @param arity
     */
    public
    NonTermPredIndicator ( AtomTerm name, int arity ) {
        super(name, arity);
    }

    /**
     * @param name
     * @param rangeTerm
     */
    public
    NonTermPredIndicator ( AtomTerm name, RangeTerm rangeTerm ) {
        super(name, rangeTerm, range);
    }
}
