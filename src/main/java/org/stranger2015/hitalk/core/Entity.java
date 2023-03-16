package org.stranger2015.hitalk.core;

import java.util.*;

/**
 *
 */
abstract public
class Entity implements IPropertyOwner, IEnumerable {
    /**
     *
     */
    private final byte kind;
    private final PredicateIndicator entityId;
    private final Map <AtomTerm, IProperty> props = new HashMap <>();
    private final Map <PredicateIndicator, PredicateDeclaration> predicateDeclTable = new HashMap <>();
    private final Map <AtomTerm, List <Operator>> opTable = new HashMap <>();
    private final IEnumerable entity;
    /**
     *
     */
    private final Set <PredicateIndicator> namespace = new HashSet <>();
    private final List <Predicate> predicates = new ArrayList <>();

    /**
     * @param kind
     * @param entityId
     * @param entity
     */
    protected
    Entity ( byte kind, PredicateIndicator entityId, IEnumerable entity ) {
        this.kind = kind;
        this.entityId = entityId;
        this.entity = entity;
    }

    /**
     * @return
     */
    @Override
    public
    IEnumerable getNext () {
        return entity;
    }

    /**
     * @return
     */
    public
    byte getKind () {
        return kind;
    }

    /**
     * @return
     */
    public
    PredicateIndicator getEntityId () {
        return entityId;
    }

    /**
     * @return
     */
    @Override
    public
    Map <AtomTerm, IProperty> getProperties () {
        return props;
    }

    /**
     * @return
     */
    public
    List <Predicate> getPredicates () {
        return predicates;
    }

    public
    Set <PredicateIndicator> getNamespace () {
        return namespace;
    }

    public
    Map <PredicateIndicator, PredicateDeclaration> getPredicateDeclTable () {
        return predicateDeclTable;
    }

    public
    Map <AtomTerm, List <Operator>> getOpTable () {
        return opTable;
    }

    public
    List <Predicate> getPredicate ( PredicateIndicator predicateIndicator ) {

        return null;
    }
}
