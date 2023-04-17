package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

/**
 *
 */
public
class PredicateDeclaration implements IProperty{
    protected final PredicateIndicator predicateMode;
    protected final AtomTerm name;
    private final Term value;

    /**
     * @param predicateMode
     * @param name
     * @param value
     */
    @Contract(pure = true)
    public
    PredicateDeclaration ( PredicateIndicator predicateMode, AtomTerm name, Term value ) {
        this.predicateMode = predicateMode;
        this.name = name;
        this.value = value;
    }

    /**
     * @return
     */
    public
    PredicateIndicator getPredicateMode () {
        return predicateMode;
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
     * @return
     */
    @Override
    public
    Term getValue () {
        return value;
    }
}
