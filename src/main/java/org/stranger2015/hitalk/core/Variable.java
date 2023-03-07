package org.stranger2015.hitalk.core;

public
class Variable extends Term{

    protected
    Variable ( ) {
        super(head);
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
    boolean isFree () {
        return value == this;
    }
}
