package org.stranger2015.hitalk.core;

import java.util.Map;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.PredicateIndicator.EQualifier.DOUBLE_SLASH;
import static org.stranger2015.hitalk.core.PredicateIndicator.EQualifier.SLASH;

/**
 *
 */
public
class PredicateIndicator extends CompoundTerm {

    private final AtomTerm name;
    private final RangeTerm range;
    private final boolean slash;
    private Map <PredicateIndicator, PredicateDeclaration> predicateDeclTable;


    /**
     * @param name
     * @param arity
     */
    public
    PredicateIndicator ( AtomTerm name,boolean slash ,int arity) {
        super(name, new CompoundTerm(slash ? SLASH.getAtomTerm() : DOUBLE_SLASH.getAtomTerm(), 2), arity));
        this.slash = slash;
    }

    /**
     * @param name
     * @param slash
     * @param rangeTerm
     */
    public
    PredicateIndicator ( AtomTerm name, boolean slash, RangeTerm rangeTerm) {
        super(name, slash ? SLASH.getAtomTerm() : DOUBLE_SLASH.getAtomTerm(), rangeTerm);
        this.slash = slash;
    }

    /**
     * @return
     */
    @Override
    public
    AtomTerm getName () {
        return name;
    }

    /**
     *
     */
    enum EQualifier {
        SLASH(createAtom("/")),
        DOUBLE_SLASH(createAtom("//")),
        ;

        private final AtomTerm atomTerm;

        /**
         * @param atomTerm
         */
        EQualifier ( AtomTerm atomTerm ) {
            this.atomTerm = atomTerm;
        }

        /**
         * @return
         */
        public
        AtomTerm getAtomTerm () {
            return atomTerm;
        }
    }

//    /**
//     * @param name
//     * @param arity
//     */
//    public
//    PredicateIndicator ( AtomTerm name, boolean slash, int arity ) {
//        super(slash ? SLASH.getAtomTerm() : DOUBLE_SLASH.getAtomTerm(), arity);
//    }
}