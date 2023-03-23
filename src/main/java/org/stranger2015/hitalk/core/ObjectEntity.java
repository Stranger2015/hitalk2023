package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public
class ObjectEntity extends Entity {

    private final List <Relation> relations=new ArrayList <>();
    private final List <Directive> directives = new ArrayList<>();
    private final List <Clause> clauses=new ArrayList<>();

    /**
     * @param entityId
     */
    protected
    ObjectEntity ( PredicateIndicator entityId,
                   List <Relation> relations,
                   List <Directive> directives) {

        super(entityId, relations, directives);

    }

    public static
    ObjectEntity create ( PredicateIndicator name,
                          List <Relation> relations,
                          List <Directive> directives) {

        return new ObjectEntity(name, relations, directives);
    }

    /**
     * @return
     */
    @Override
    public
    IEnumerable getNext () {
        return null;
    }

    public
    List <Clause> getClauses (Map<PredicateIndicator, PredicateDefinition> predicateDefinitions) {
        return clauses;
    }

    /**
     * @return
     */
    @Override
    public
    Map <AtomTerm, IProperty> getProperties () {
        return null;
    }
}
