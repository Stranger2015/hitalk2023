package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.Preprocessor;
import org.stranger2015.hitalk.core.compiler.PrologCompiler;
import org.stranger2015.hitalk.core.compiler.PrologInputStream;
import org.stranger2015.hitalk.core.compiler.TermOptions;
import org.stranger2015.hitalk.core.runtime.CodeBase;

import java.io.IOException;

/**
 *
 */
public
class HitalkCompiler extends PrologCompiler {

    /**
     *
     */
    public
    HitalkCompiler ( HitalkParser parser, Preprocessor preprocessor, CodeBase codeBase ) {
        super(parser, preprocessor, codeBase );
    }

    /**
     * @param stream
     * @param options
     * @return
     */
    @Override
    public
    Clause readClause ( PrologInputStream stream, TermOptions options ) throws IOException {
        return super.readClause(stream, options);
    }
}
