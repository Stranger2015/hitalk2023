package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.MemoryCell;

/**
 *
 */
public abstract
class Term extends MemoryCell {
    public static final byte ATOM = 0b1000000;
    public static final byte INTEGER = 0b1100000;
    public static final byte FLOAT = 0b1010000;
    public static final byte VAR = 0b0000000;
    public static final byte COMPOUND = 0b0100000;
    public static final byte LIST = 0b0110000;

    public ListTerm value;

    protected
    Term ( ListTerm head ) {
        this();
        value = head;
    }

    /**
     * @param type
     */
    public
    Term ( ETypeMemoryCells type ) {
        super();
        switch (type){
            case STR -> {

            }
            case FN -> {
            }
            case REF -> {
            }
            case CON -> {
            }
            case NUM -> {
            }
            case LIS -> {
            }
        }

    }

    protected
    Term () {
    }

    /**
     * @return
     */
    abstract public
    byte getKind ();

    public
    void resolveTerm () {
    }

    public
    boolean isCompound () {
        return false;
    }

    public
    boolean isVar () {
        return false;
    }

    public
    boolean isNonVar () {
        return false;
    }

    /**
     * @return
     */
    public abstract
    boolean isFree();

    /**
     * @return
     */
    public
    boolean isBound () {
        return !isFree();
    }

    public
    void set ( int domain, int frame, int index ) {

    }

    protected
    ListTerm getTail () {
        if(!isList()) {
            return null;
        }

        ListTerm listTerm= (ListTerm) this;
        return listTerm.getTail();
    }

    private
    boolean isList () {
    }
//
//    public
//    boolean lowerThan ( CellAddress hb ) {
//        return super.lowerThan(hb);
//    }
}
