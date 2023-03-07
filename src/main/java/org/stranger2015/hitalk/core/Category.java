package org.stranger2015.hitalk.core;

/**
 *
 */
public
class Category extends Entity {

    /**
     * @param kind
     * @param entityId
     */
    protected
    Category ( byte kind, CompoundTerm entityId, IEnumerable category) {
        super(kind, entityId, category);
    }
}
