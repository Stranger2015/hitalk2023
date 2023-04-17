package org.stranger2015.hitalk.core;

public
class DoubleTerm extends NumberTerm {
    private final double v;

    public
    DoubleTerm ( double v ) {
        this.v = v;
    }

    /**
     * @return
     */
    @Override
    public
    boolean isPredicateIndicator () {
        return false;
    }

    /**
     * @return
     */
    @Override
    public
    boolean isAtomic () {
        return true;
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
    double getV () {
        return v;
    }
}

