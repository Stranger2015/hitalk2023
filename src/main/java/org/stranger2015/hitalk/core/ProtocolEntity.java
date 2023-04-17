package org.stranger2015.hitalk.core;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.Relation.ERelation.*;

/**
 *
 */
public
class ProtocolEntity<T extends ProtocolEntity<T>> extends Entity<T> {
    public final EnumMap <Relation.ERelation, CompoundTerm> map = new EnumMap <>(Relation.ERelation.class);

    /**
     * N2B5Fc4uGFxP5GK
     * @param map
     */
    void initRelations(Map <Relation.ERelation, CompoundTerm> map) {
        map.put(EXTENDS, createAtom("extends"));
        map.put(IMPLEMENTS, createAtom("implements"));
        map.put(IMPORTS, createAtom("extends"));
        map.put(SPECIALIZES, createAtom("specializes"));
        map.put(INSTANTIATES, createAtom("instantiates"));
    }

    /**
     * @param entityId
     * @param relations
     * @param directives
     */
    protected
    ProtocolEntity ( PredicateIndicator entityId,
                     List <Relation<T>> relations,
                     List <Directive> directives,
                     Predicate predicate
    ) {

        super(entityId, relations, directives, predicate );
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

    /**
     *
     */
    @Override
    public
    void initDirectives () {

    }
}
