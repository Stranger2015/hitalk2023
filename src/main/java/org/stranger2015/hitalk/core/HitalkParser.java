package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.PrologInputStream;

/**
 *
 */
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

    /**
     * Parses next term from the stream built on string.
     *
     * @param endNeeded <tt>true</tt> if it is required to parse the end token
     *                  (a period), <tt>false</tt> otherwise.
     * @throws IllegalArgumentException if a syntax error is found.
     */
    @Override
    public
    Term nextTerm ( boolean endNeeded ) throws IllegalArgumentException {
        return super.nextTerm(endNeeded);
    }
}
