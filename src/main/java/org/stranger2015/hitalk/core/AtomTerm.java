package org.stranger2015.hitalk.core;

/**
 *
 */
public
class AtomTerm extends CompoundTerm implements IAtomic {
    public static final AtomTerm IMPLIES = createAtom(":-");
    public static final AtomTerm BYPASS = createAtom("{}");
    public static final AtomTerm MINUS = createAtom("-");
    public static final AtomTerm STATIC = createAtom("static");
    public static final AtomTerm DYNAMIC = createAtom("dynamic");
    public static final AtomTerm ENCODING = createAtom("encoding");
    public static final AtomTerm OBJECT = createAtom("object");
    public static final AtomTerm CATEGORY = createAtom("category");
    public static final AtomTerm PROTOCOL = createAtom("protocol");
    public static final AtomTerm USE_MODULE = createAtom("use_module");
    public static final AtomTerm END_OBJECT = createAtom("end_object");
    public static final AtomTerm END_CATEGORY = createAtom("end_category");
    public static final AtomTerm END_PROTOCOL = createAtom("end_protocol");
    public static final AtomTerm HILOG_APPLY = createAtom("$hilog_apply");

    /**
     *
     */
    public
    AtomTerm ( int data ) {
        super(MINUS, data);
    }

    public static
    int getId () {
        return id;
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
