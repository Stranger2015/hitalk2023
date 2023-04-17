package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.AtomTerm;
import org.stranger2015.hitalk.core.ListTerm;

/**
 *
 */
public
class ProtocolIdentifier extends EntityIdentifier{
    /**
     * @param name
     */
    protected
    ProtocolIdentifier ( AtomTerm name ) {
        super(new ListTerm(name, ListTerm.EMPTY_LIST));
    }
}
