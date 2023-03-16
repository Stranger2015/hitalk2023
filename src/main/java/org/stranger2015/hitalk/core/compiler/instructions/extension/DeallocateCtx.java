package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public class DeallocateCtx implements Instruction {

	/**
	 * @param runtime
	 */
	@Override
	public void execute( PrologRuntime runtime) {
		runtime.popEnvironment();
	}

	/**
	 * @return
	 */
	public String toString(){ return "deallocate_ctx";	}
}
