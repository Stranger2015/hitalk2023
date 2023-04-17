package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.PredicateIndicator;
import org.stranger2015.hitalk.core.compiler.instructions.extension.EntityIdentifier;

/**
 *
 */
public
class CtxFrame extends Frame {

private EntityIdentifier entityId;// ListTerm goal

    protected
    CtxFrame ( int cp_index, CodeClause cp_clause, int variableCount, int b0 ) {
        super(cp_index, cp_clause, variableCount, b0);
    }

    /**
     * @return
     */
    public
    EntityIdentifier getEntityId () {
        return entityId;
    }

    /**
     * @return
     */
    @Override
    public
    String toString () {
        return "CtxFrame %s".formatted(super.toString());
    }

    public
    void setEntityId ( EntityIdentifier entityId ) {
        this.entityId = entityId;
    }
}
