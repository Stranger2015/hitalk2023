package org.stranger2015.hitalk.core;

/**
 *
 */
public
class IntTerm extends Term {

    /**
     * @param value
     */
    public
    IntTerm ( int value ) {
        super(value);
    }

    /**
     * @return
     */
    @Override
    public
    byte getKind () {
        return 0;
    }

    @Override
    public
    boolean isPredicateIndicator () {
        return false;
    }

    @Override
    public
    boolean isAtomic () {
        return false;
    }

    /**
     * @return
     */
    @Override
    public
    boolean isFree () {
        return false;
    }

    public
    ListTerm getInt () {
        return null;
    }
}
