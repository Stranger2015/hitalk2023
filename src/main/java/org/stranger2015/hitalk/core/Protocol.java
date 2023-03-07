package org.stranger2015.hitalk.core;

/**
 *
 */
public
class Protocol extends Entity {
    /**
     * @param kind
     * @param entityId
     */
    protected
    Protocol ( byte kind, CompoundTerm entityId, IEnumerable protocol ) {
        super(kind, entityId, protocol);
    }
}
