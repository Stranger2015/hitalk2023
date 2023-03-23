package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public
class CategoryEntity extends Entity {

    private final List <Relation> relations = new ArrayList <>();
    private final List <Directive> directives = new ArrayList <>();
//    private final List <Clause> clauses = new ArrayList <>();
    private final Map <AtomTerm, IProperty> properties = new HashMap <>();

    /**
     * @param entityId
     * @param relations
     * @param directives
     */
    protected
    CategoryEntity ( PredicateIndicator entityId,
                     List <Relation> relations,
                     List <Directive> directives
    ) {
        super(entityId, relations, directives);
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
    List <Relation> getRelations () {
        return relations;
    }

    public
    List <Directive> getDirectives () {
        return directives;
    }
}
