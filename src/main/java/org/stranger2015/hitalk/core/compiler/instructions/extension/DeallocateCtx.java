package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime;

public class DeallocateCtx implements Instruction {

	public void execute( PrologRuntime runtime) {
		runtime.popEnvironment();
	}
	
	public String toString(){ return "deallocate_ctx";	}
}
