package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class EndOfQuery implements Instruction {
	/**
	 * See the WAM tutorial for explanation on the execute functionality of each instruction.
	 *
	 * @param runtime
	 */
	public void execute( PrologRuntime runtime) {
		runtime.setFinished(true);
	}
	
	public String toString(){
		return "End of Query";
	}
}