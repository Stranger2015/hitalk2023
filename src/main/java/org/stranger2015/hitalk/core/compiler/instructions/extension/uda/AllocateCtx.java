package org.stranger2015.hitalk.core.compiler.instructions.extension.uda;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public
class AllocateCtx implements Instruction {
    /**
     * @param runtime
     */
    @Override
    public
    void execute ( PrologRuntime runtime ) {
        runtime.popEnvironment();
    }

    /**
     * @return
     */
    public
    String toString () {
        return "allocate_ctx";
    }
}
