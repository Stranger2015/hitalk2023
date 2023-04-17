package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

/**
 *
 */
public
class Relation<T extends Entity<T>> extends CompoundTerm {
    /**
     *
     */

    private final Enum<?> relation;

    /**
     * @param listTerm
     */
    @Contract(pure = true)
    public
    Relation ( Enum<?> relation, ListTerm listTerm ) {
        super(listTerm);

        this.relation = relation;
    }

    /**
     * @return
     */
    public
    Enum<?> getRelation () {
        return relation;
    }

    /**
     *
     */
    enum ERelation {
        EXTENDS,
        IMPLEMENTS,
        IMPORTS,
        INSTANTIATES,
        SPECIALIZES,
        COMPLEMENTS,
    }
}
