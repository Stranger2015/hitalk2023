package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public
class Library implements IEnumerable {
    private final AtomTerm name;
    private
    final List <PredicateDefinition> predicates = new ArrayList <>();

    /**
     * @param name
     * @param predicates
     */
    public
    Library ( AtomTerm name, List <PredicateDefinition> predicates ) {
        this.name = name;
        this.predicates.addAll(predicates);
    }

    /**
     * @return
     */
    public
    List <PredicateDefinition> getPredicates () {
        return predicates;
    }

    /**
     * @return
     */
    public
    AtomTerm getName () {
        return name;
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
