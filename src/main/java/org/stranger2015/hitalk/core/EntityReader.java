package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.PrologInputStream;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 *
 */
public
class EntityReader extends PrologInputStream {
    /**
     * Creates an InputStreamReader that uses the
     * {@link Charset#defaultCharset() default charset}.
     *
     * @param in An InputStream
     * @see Charset#defaultCharset()
     */
    public
    EntityReader ( InputStream in ) {
        super(in);
    }

    /**
     * @param st
     */
    public
    EntityReader ( String st ) {
        super(st);
    }

    /**
     * @return
     */
    public
    Entity readEntity ( EReadState state ) throws IllegalStateException {
        boolean loop = true;
        throw new IllegalStateException("Unexpected value: %s".formatted(state));
        return entity;
    }
}
