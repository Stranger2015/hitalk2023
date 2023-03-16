package org.stranger2015.hitalk.core;

/**
 *
 */
public
class AtomTerm extends CompoundTerm implements IAtomic {
    public static final AtomTerm IMPLIES = createAtom(":-");
    public static final AtomTerm BYPASS = createAtom("{}");
    public static final AtomTerm MINUS = createAtom("-");

    /**
     *
     */
    public
    AtomTerm ( int data ) {
        super(AtomTerm.MINUS, data);
    }

    public static
    int getId () {
        return id;
    }

    /**
     * @return
     */
    @Override
    public
    byte getKind () {
        return 0;
    }

    private static int id = 0;

    /**
     * @param s
     * @return
     */
    public static
    AtomTerm createAtom ( String s ) {
        id++;
        return new AtomTerm(id);
    }

    /**
     *
     */
    @Override
    public
    void resolveTerm () {

    }

    /**
     * @return
     */
    @Override
    public
    boolean isAtomic () {
        return true;
    }

    @Override
    public
    boolean isPredicateIndicator () {
        return false;
    }

    @Override
    public
    boolean isVar () {
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
}
