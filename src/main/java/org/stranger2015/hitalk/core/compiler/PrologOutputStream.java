package org.stranger2015.hitalk.core.compiler;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 */
public
class PrologOutputStream extends OutputStreamWriter {

    /**
     * Creates an OutputStreamWriter that uses the default character encoding.
     *
     * @param out An OutputStream
     */
    public
    PrologOutputStream ( @NotNull OutputStream out ) {
        super(out);
    }
}
