package org.stranger2015.hitalk.core;

public
class ModuleEntity extends Entity {
     /**
      * @param entityId
      */
    public ModuleEntity ( PredicateIndicator entityId ) {
        super( entityId,new Predicate(entityId.getName()));
    }

    /**
     *
     */
    @Override
    public
    void initDirectives () {

    }

    /**
     * @return
     */
    @Override
    public
    IEnumerable getNext () {
        return null;
    }
}
