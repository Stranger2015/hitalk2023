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
    IntTerm ( ListTerm value ) {
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
        return value;
    }
}
