package org.stranger2015.hitalk.core;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.Relation.ERelation.*;

/**
 *
 * implements_protocols
 * extends_categories
 * complements_objects
 */
public
class CategoryEntity<T extends CategoryEntity<T>> extends Entity<T> {
    public final EnumMap <Relation.ERelation, CompoundTerm> map = new EnumMap <>(Relation.ERelation.class);

    void initRelations(Map <Relation.ECatgory CompoundTerm> map) {
        map.put(EXTENDS, createAtom("extends"));
        map.put(IMPLEMENTS, createAtom("implements"));
        map.put(COMPLEMENTS, createAtom("implerments"));3

    }

    /**
     * @param entityId
     * @param relations
     * @param directives
     * @param predicate
     */
    protected
    CategoryEntity ( PredicateIndicator entityId,
                     List <Relation<T>> relations,
                     List <Directive> directives,
                     Predicate predicate
    ) {
        super(entityId, relations, directives, predicate);
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
    List <Directive> getDirectives () {
        return directives;
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
    Map <AtomTerm, IProperty> getProperties () {
        return properties;
    }
}
