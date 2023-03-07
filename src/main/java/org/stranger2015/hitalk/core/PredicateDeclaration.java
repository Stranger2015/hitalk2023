package org.stranger2015.hitalk.core;

/**
 *
 */
public
class PredicateDeclaration {
    private final PredicateIndicator predicateMode;

    public
    PredicateDeclaration ( PredicateIndicator predicateMode ) {
        this.predicateMode = predicateMode;
    }

    /**
     * @return
     */
    public
    PredicateIndicator getPredicateMode () {
        return predicateMode;
    }
}
