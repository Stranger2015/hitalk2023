package org.stranger2015.hitalk.core.compiler.instructions.extension.uda;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public
class CallLazy implements Instruction {
    /**
     * See the WAM tutorial for explanation on the execute functionality of each instruction.
     *
     * @param runtime
     */
    @Override
    public
    void execute ( PrologRuntime runtime ) {

    }

    /**
     * @return
     */
    @Override
    public
    String toString () {
        return "call_lazy";
    }
}
