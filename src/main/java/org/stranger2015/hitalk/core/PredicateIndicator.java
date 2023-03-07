package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

import static org.stranger2015.hitalk.core.PredicateIndicator.Qualifier.*;

/**
 *
 */
public
class PredicateIndicator extends CompoundTerm {
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
    IntTerm getArity () {
        return arity;
    }

    /**
     *
     */
    enum Qualifier {
        SLASH(new AtomTerm("/")),
        DOUBLE_SLASH(new AtomTerm("//")),
        ;

        private final AtomTerm atomTerm;

        /**
         * @param atomTerm
         */
        @Contract(pure = true)
        Qualifier ( AtomTerm atomTerm ) {
            this.atomTerm = atomTerm;
        }

        /**
         * @return
         */
        @Contract(pure = true)
        public
        AtomTerm getAtomTerm () {
            return atomTerm;
        }
    }

    private final AtomTerm name;
    private final IntTerm arity;

    /**
     * @param name
     * @param arity
     */
    public
    PredicateIndicator ( Term name, boolean slash, IntTerm arity ) {
        super(qualifiedName, name, arg2);

        this.name = slash ? SLASH.getAtomTerm() : DOUBLE_SLASH.getAtomTerm();
        this.arity = arity;
    }
}

