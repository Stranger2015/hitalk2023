package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
abstract public
class Entity<T extends Entity<T>> implements IPropertyOwner, IEnumerable {
    protected final PredicateIndicator entityId;
    protected final List <Relation<T>> relations = new ArrayList <>();
    protected final List <Directive> directives = new ArrayList <>();
    protected final List <Directive> entityDirectives = new ArrayList <>();
    protected final Map <PredicateIndicator, PredicateDeclaration> predicateDeclTable = new HashMap <>();
    protected final Map <AtomTerm, IProperty> properties = new HashMap <>();

    protected final Predicate predicate;

    /**
     * @param entityId
     * @param relations
     * @param directives
     * @param predicate
     */
    public
    Entity ( PredicateIndicator entityId,
             List <Relation<T>> relations,
             List <Directive> directives,
             Predicate predicate ) {

        this(entityId, predicate);

        this.relations.addAll(relations);
        this.directives.addAll(directives);
    }

    /**
     * @param entityId
     * @param predicate
     */
    public
    Entity ( PredicateIndicator entityId, Predicate predicate ) {
        this.entityId = entityId;
        this.predicate = predicate;
    }

    /**
     * @return
     */
    @Override
    public
    Map <AtomTerm, IProperty> getProperties () {
        return properties;
    }

    /**
     * @param properties
     */
    public
    void setProperties ( Map <AtomTerm, IProperty> properties ) {
        this.properties.putAll(properties);
    }

    /**
     * @return
     */
    public
    List <Relation<T>> getRelations () {
        return relations;
    }

    /**
     * @return
     */
    public
    List <Directive> getDirectives () {
        return entityDirectives;
    }

    /**
     * @return
     */
    public
    PredicateIndicator getEntityId () {
        return entityId;
    }

    /**
     * @param predicateIndicator
     * @return
     */
    public
    Predicate getPredicate ( PredicateIndicator predicateIndicator ) {
        return predicate;
    }

    /**
     *
     */
    enum ERelation {
        PROTOTYPE,
        NON_PROTOTYPE,
    }

    /**
     *
     */
    enum EPrototype {
        IMPLEMENTS_PROTOCOLS,
        IMPORTS_CATEGORIES,
        EXTENDS_OBJECTS
    }

    /**
     *
     */
    enum ENonPrototype {
        IMPLEMENTS_PROTOCOLS,
        IMPORTS_CATEGORIES,
        INSTANTIATES_CLASSES,
        SPECIALIZES_CLASSES
    }

    enum ECategory {
        IMPLEMENTS_PROTOCOLS,
        EXTENDS_CATEGORIES,
        COMPLEMENTS_OBJECTS,
    }

    /**
     *
     */
    enum EPredicateDirective {
        ALIAS_DIRECTIVE,
        SYNCHRONIZED_DIRECTIVE,
        USES_DIRECTIVE,
        USE_MODULE_DIRECTIVE,
        SCOPE_DIRECTIVE,
        MODE_DIRECTIVE,
        META_PREDICATE_DIRECTIVE,
        META_NON_TERMINAL_DIRECTIVE,
        INFO_DIRECTIVE,
        DYNAMIC_DIRECTIVE,
        DISCONTIGUOUS_DIRECTIVE,
        MULTIFILE_DIRECTIVE,
        COINDUCTIVE_DIRECTIVE,
        OPERATOR_DIRECTIVE,
    }

    /**
     *
     */
//    enum ECategoryProperty{
////            STATIC,
////                DYNAMIC,
////                BUILT_IN,
////                “FILE(” ATOM “)”,
////                “FILE(” ATOM “,” ATOM “)”,
////                “LINES(” INTEGER “,” INTEGER2 “)”,
////                “EVENTS”,
////                “SOURCE_DATA”,
////                “PUBLIC(” ENTITY_RESOURCES_LIST “)”,
////                “PROTECTED(” ENTITY_RESOURCES_LIST “)”,
////                “PRIVATE(” ENTITY_RESOURCES_LIST “)”,
////                “DECLARES(” PREDICATE_INDICATOR “,” PREDICATE_DECLARATION_PROPERTY_LIST “)”,
////                “DEFINES(” PREDICATE_INDICATOR “,” PREDICATE_DEFINITION_PROPERTY_LIST “)”,
////                “INCLUDES(” PREDICATE_INDICATOR “,” OBJECT_IDENTIFIER, CATEGORY_IDENTIFIER “,” PREDICATE_DEFINITION_PROPERTY_LIST “)”,
////                “PROVIDES(” PREDICATE_INDICATOR “,” OBJECT_IDENTIFIER, CATEGORY_IDENTIFIER “,” PREDICATE_DEFINITION_PROPERTY_LIST “)”,
////                “ALIAS(” PREDICATE_INDICATOR “,” PREDICATE_ALIAS_PROPERTY_LIST “)”,
////                “CALLS(” PREDICATE “,” PREDICATE_CALL_UPDATE_PROPERTY_LIST “)”,
////                “UPDATES(” PREDICATE “,” PREDICATE_CALL_UPDATE_PROPERTY_LIST “)”,
////                “NUMBER_OF_CLAUSES(” INTEGER “)”,
////                “NUMBER_OF_RULES(” INTEGER “)”,
////                “NUMBER_OF_USER_CLAUSES(” INTEGER “)”,
////                “NUMBER_OF_USER_RULES(” INTEGER “)”,
////                DEBUGGING
////    }
////    public static final Map <PredicateIndicator, Consumer <PredicateIndicator>> directives = new HashMap <>();
////    public static final Map <PredicateIndicator, PredicateDeclaration> predicateDeclarationTable = new HashMap <>();
////    public static final Map <PredicateIndicator, PredicateDeclaration> predicateDefinionTable = new HashMap <>();
////

    /**
     *
     */
    public abstract
    void initDirectives ();

    //    private final PredicateIndicator entityId;
//    private final Map <AtomTerm, IProperty> props = new HashMap <>();
//    private final Map <PredicateIndicator, PredicateDeclaration> predicateDeclTable = new HashMap <>();
    protected final Map <AtomTerm, List <Operator>> opTable = new HashMap <>();

    //    /**
//     *
//     */
//    private final Set <PredicateIndicator> namespace = new HashSet <>();
//    private final List <Predicate> predicates = new ArrayList <>();
//
//    /**
//     * @param entityId
//     * @param relations
//     * @param directives
//     */
//    protected
//    Entity ( PredicateIndicator entityId,List <Relation> relations, List <Directive> directives ) {
//        this.entityId = entityId;
//    }
//
//        ECategoryProperty ( PredicateIndicator entityId ) {
//            this.entityId = entityId;
//        }
//
//        /**
//     * @return
//     */
//    protected
//    boolean isStatic () {
//        return props.get(STATIC) != null;
//    }
//
//    /**
//     * @return
//     */
//    public
//    PredicateIndicator getEntityId () {
//        return entityId;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public
//    Map <AtomTerm, IProperty> getProperties () {
//        return props;
//    }
//
//    /**
//     * @return
//     */
//    public
//    List <Predicate> getPredicates () {
//        return predicates;
//    }
//
//    /**
//     * @return
//     */
//    public
//    Set <PredicateIndicator> getNamespace () {
//        return namespace;
//    }
//
    public
    Map <PredicateIndicator, PredicateDeclaration> getPredicateDeclTable () {
        return predicateDeclTable;
    }

    /**
     * @return
     */
    public
    Map <AtomTerm, List <Operator>> getOpTable () {
        return opTable;
    }
}
