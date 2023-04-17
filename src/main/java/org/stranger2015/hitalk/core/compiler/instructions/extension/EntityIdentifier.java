package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.CompoundTerm;
import org.stranger2015.hitalk.core.ListTerm;

/**
 *
 */
public abstract
class EntityIdentifier extends CompoundTerm {
    /**
     * @param nameArgs
     */
    protected
    EntityIdentifier ( ListTerm nameArgs ) {
        super(nameArgs);
    }
}
