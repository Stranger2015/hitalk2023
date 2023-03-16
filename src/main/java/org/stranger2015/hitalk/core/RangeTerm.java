package org.stranger2015.hitalk.core;

/**
 *
 */
public
class RangeTerm extends CompoundTerm{
    public
    RangeTerm ( int i , int j) {
        super(AtomTerm.MINUS, i << 16 | j );
    }
}
