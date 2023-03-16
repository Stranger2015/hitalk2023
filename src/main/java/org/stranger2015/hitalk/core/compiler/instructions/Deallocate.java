package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class Deallocate implements Instruction {

	public void execute( PrologRuntime runtime) {
		runtime.popEnvironment();
	}

	/**
	 * @return
	 */
	public String toString(){ return "deallocate";	}
}
