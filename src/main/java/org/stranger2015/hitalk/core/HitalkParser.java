package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.PrologInputStream;

public
class HitalkParser extends PrologParser {
    /**
     * creating a HitalkParser specifying how to handle operators
     * and what text to parse
     *
     * @param op
     * @param stream
     */
    public
    HitalkParser ( OperatorManager op, PrologInputStream stream ) {
        super(op, stream);
    }
}
