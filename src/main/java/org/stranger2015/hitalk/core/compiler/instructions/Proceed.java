package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class Proceed implements Instruction {

	/**
	 * @param runtime
	 */
	@Override
	public void execute( PrologRuntime runtime) {
		runtime.moveToContinuationInstruction();
	}

	/**
	 * @return
	 */
	public String toString(){
		return "proceed";
	}
}
