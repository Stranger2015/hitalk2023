package org.stranger2015.hitalk.core;

import java.util.List;

/**
 *
 */
public
class PredicateDefinition extends PredicateDeclaration {
    private final List <Clause> code;

    /**
     * @param predicateIndicator
     * @param code
     */
    public
    PredicateDefinition ( PredicateIndicator predicateIndicator, List <Clause> code ) {
        super(predicateIndicator);
        this.code = code;
    }

    /**
     * @return
     */
    public
    List <Clause> getCode () {
        return code;
    }
}
