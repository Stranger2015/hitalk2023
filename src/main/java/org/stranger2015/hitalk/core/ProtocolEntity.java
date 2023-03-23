package org.stranger2015.hitalk.core;

import java.util.List;
import java.util.Map;

/**
 *
 */
public
class ProtocolEntity extends Entity {

    /**
     * @param entityId
     * @param relations
     * @param directives
     */
    protected
    ProtocolEntity ( PredicateIndicator entityId,
                     List <Relation> relations,
                     List <Directive> directives ) {

        super(entityId, relations, directives, name);
    }

    /**
     * @return
     */
    @Override
    public
    IEnumerable getNext () {
        return null;
    }

    /**
     * @return
     */
    @Override
    public
    Map <AtomTerm, IProperty> getProperties () {
        return super.getProperties();
    }

//    /**
//     * @return
//     */
//    @Override
//    public
//    IEnumerable getNext () {
//        return null;
//    }
//
//    public
//    List <Relation> getRelations () {
//        return relations;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public
//    Map <AtomTerm, IProperty> getProperties () {
//        return null;
//    }
}
