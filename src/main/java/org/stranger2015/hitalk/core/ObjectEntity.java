package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.Entity.ERelation.PROTOTYPE;
import static org.stranger2015.hitalk.core.Relation.ERelation.*;

/**
 * object(Object)
 * <p>------------------!>
 * object(Object,
 * implements(Protocols))
 * <p>
 * object(Object,
 * imports(Categories))
 * <p>
 * object(Object,
 * implements(Protocols),
 * imports(Categories))
 * <p>
 * Prototype extensions
 * <p>
 * object(Object,
 * extends(Objects))
 * <p>
 * object(Object,
 * implements(Protocols),
 * extends(Objects))
 * <p>
 * object(Object,
 * imports(Categories),
 * extends(Objects))
 * <p>
 * object(Object,
 * implements(Protocols),
 * imports(Categories),
 * extends(Objects))
 * <p>
 * Class instances
 * <p>
 * object(Object,
 * instantiates(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * instantiates(Classes))
 * <p>
 * object(Object,
 * imports(Categories),
 * instantiates(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * imports(Categories),
 * instantiates(Classes))
 * <p>
 * Classes
 * <p>
 * object(Object,
 * specializes(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * specializes(Classes))
 * <p>
 * object(Object,
 * imports(Categories),
 * specializes(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * imports(Categories),
 * specializes(Classes))
 * <p>
 * Classes with metaclasses
 * <p>
 * object(Object,
 * instantiates(Classes),
 * specializes(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * instantiates(Classes),
 * specializes(Classes))
 * <p>
 * object(Object,
 * imports(Categories),
 * instantiates(Classes),
 * specializes(Classes))
 * <p>
 * object(Object,
 * implements(Protocols),
 * imports(Categories),
 * instantiates(Classes),
 * specializes(Classes))
 */
public
class ObjectEntity<T extends ObjectEntity <T>> extends Entity <T> {
    public final EnumMap <Relation.ERelation, CompoundTerm> map
            = new EnumMap <>(Relation.ERelation.class);

    public
    ObjectEntity ( Term head,  Relation<T> relations ) {
        super(head, relations);
    }

    /**
     * @param map
     */
    protected
    void initRelations ( Map <Relation.ERelation, CompoundTerm> map ) {
        map.put(EXTENDS, createAtom("extends"));
        map.put(IMPLEMENTS, createAtom("implements"));
        map.put(IMPORTS, createAtom("extends"));
        map.put(SPECIALIZES, createAtom("specializes"));
        map.put(INSTANTIATES, createAtom("instantiates"));
    }

    /**
     * @param entityId
     */
    protected
    ObjectEntity ( PredicateIndicator entityId,
                   List <Relation <T>> relations,
                   List <Directive> directives,
                   Predicate predicate ) {

        super(entityId, relations, directives, predicate);
    }

    /**
     * @param name
     * @param relations
     * @param directives
     * @param predicate
     * @return
     */
    @Contract("_, _, _, _ -> new")
    public static @NotNull
    <T extends ObjectEntity <T>> ObjectEntity <T> create (
            PredicateIndicator name,
            List <Relation <T>> relations,
            List <Directive> directives,
            Predicate predicate ) {

        return new ObjectEntity <>(name, relations, directives, predicate);
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
        return properties;
    }

    /**
     *
     */
    @Override
    public
    void initDirectives () {
        Directive d = new Directive(new DirectiveProc(){
            /**
             *
             */
            @Override
            public
            void execute () {

            }

            /**
             * Applies this function to the given argument.
             *
             * @param o the function argument
             * @return the function result
             */
            @Override
            public
            Object apply ( Object o ) {
                return null;
            }

            /**
             * Applies this function to the given argument.
             *
             * @param compoundTerm the function argument
             * @return the function result
             */
            @Override
            public
            Entity <T> apply ( CompoundTerm compoundTerm ) {
                ListTerm args = compoundTerm.getArgs();

                RangeTerm arityRange = args.getLength().getArityRange();
                int arity1 = arityRange.getArityLow();
                int arity2 = arityRange.getArityHigh();
                Term arg;
                Term object = args.getArg(0);

                for (int i = 0; i < arity2; i++) {
                    arg = args.getArg(i);
                    relations.add(new Relation <>(PROTOTYPE,args));
                }

                return new ObjectEntity <>(compoundTerm.getArgs().getHead(),relations );
            }

//            createObject ();
        });
    }
}

