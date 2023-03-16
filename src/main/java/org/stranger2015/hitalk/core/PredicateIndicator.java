package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.PredicateIndicator.Qualifier.DOUBLE_SLASH;
import static org.stranger2015.hitalk.core.PredicateIndicator.Qualifier.SLASH;

/**
 *
 */
public
class PredicateIndicator extends CompoundTerm {
    public
    PredicateIndicator ( AtomTerm head, int arity ) {
        super(head, arity);
    }

    @Override
    public
    AtomTerm getName () {
        return name;
    }

    /**
     * @return
     */
    @Override
    public
    int getArity () {
        return arity;
    }

    /**
     *
     */
    enum Qualifier {
        SLASH(createAtom("/")),
        DOUBLE_SLASH(createAtom("//")),
        ;

        private final AtomTerm atomTerm;

        /**
         * @param atomTerm
         */
        Qualifier ( AtomTerm atomTerm ) {
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

    private final AtomTerm name;
    private final int arity;

    /**
     * @param name
     * @param arity
     */
    public
    PredicateIndicator ( AtomTerm name, boolean slash, int arity ) {
        super(qualifiedName, name, arg2);

        this.name = slash ? SLASH.getAtomTerm() : DOUBLE_SLASH.getAtomTerm();
        this.arity = arity;
    }
}