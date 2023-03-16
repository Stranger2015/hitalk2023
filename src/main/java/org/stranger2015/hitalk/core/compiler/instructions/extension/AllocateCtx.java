package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public
class AllocateCtx implements Instruction {
    public
    void execute ( PrologRuntime runtime ) {
        runtime.popEnvironment();
    }

    public
    String toString () {
        return "allocate_ctx";
    }
}
