package org.stranger2015.hitalk.core.compiler;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Equivalent to read_term/3, but sets options according to the current compilation context and
 * optionally processes comments.
 * Defined options:
 * <p>
 * syntax_errors(+Atom)
 *     See read_term/3, but the default is dec10 (report and restart).
 * term_position(-TermPos)
 *     Same as for read_term/3.
 * subterm_positions(-TermPos)
 *     Same as for read_term/3.
 * variable_names(-Bindings)
 *     Same as for read_term/3.
 * process_comment(+Boolean)
 *     If true (default), call prolog:comment_hook(Comments, TermPos, Term) if this multifile hook is defined
 *     (see prolog:comment_hook/3).
 *     This is used to drive PlDoc.
 * comments(-Comments)
 *     If provided, unify Comments with the comments encountered while reading Term.
 *     This option implies process_comment(false).
 * <p>
 * The singletons option of read_term/3 is initialised from the active style-checking mode.
 * The module option is initialised to the current compilation module (see prolog_load_context/2).
 */
public
class PrologInputStream extends InputStreamReader {
    /**
     * Creates an InputStreamReader that uses the
     * {@link Charset#defaultCharset() default charset}.
     *
     * @param in An InputStream
     * @see Charset#defaultCharset()
     */
    public
    PrologInputStream ( @NotNull InputStream in ) {
        super(in);
    }

    public
    PrologInputStream ( String st ) {
        super(new ByteArrayInputStream(st.getBytes(StandardCharsets.UTF_8)));
    }
}
