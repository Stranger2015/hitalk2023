package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.Preprocessor;
import org.stranger2015.hitalk.core.compiler.PrologCompiler;

/**
 *
 */
public
class HitalkCompiler extends PrologCompiler {

    /**
     *
     */
        public
    HitalkCompiler ( HitalkParser parser, Preprocessor preprocessor ) {
        super(parser, preprocessor);
    }
}
