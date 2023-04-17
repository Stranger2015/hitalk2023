package org.stranger2015.hitalk.core;

/**
 *
 */
public
class PredicateIndicator extends CompoundTerm {
public static final AtomTerm SLASH= AtomTerm.createAtom("/");
    protected final RangeTerm range;


    /**
     * @param name
     * @param range
     */
    public
    PredicateIndicator ( AtomTerm name, RangeTerm range ) {
        super(SLASH,name);
        this.range = range;
    }

    /**
     * @param name
     * @param rangeTerm
     * @param range
     */
    public
    PredicateIndicator ( AtomTerm name, RangeTerm rangeTerm, RangeTerm range ) {
        super(name, SLASH, rangeTerm);
        this.range = range;
    }
}
//  /**
//     *
//     */
//    enum EQualifier {
//        SLASH(createAtom("/")),1
//        DOUBLE_SLASH(createAtom("//")),
//        ;
//
//        private final AtomTerm atomTerm;
//
//        /**
//         * @param atomTerm
//         */
//        EQualifier ( AtomTerm atomTerm ) {
//            this.atomTerm = atomTerm;
//        }
//
//        /**
//         * @return
//         */
//        public
//        AtomTerm getAtomTerm () {
//            return atomTerm;
//        }
//    }
