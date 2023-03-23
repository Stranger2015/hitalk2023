package org.stranger2015.hitalk.core;

public
class ModuleEntity extends Entity {
     /**
     *
     * @param entityId
     * @param entity
     */
    public ModuleEntity ( PredicateIndicator entityId, IEnumerable entity ) {
        super( entityId, entity, directives, name);
    }
}
