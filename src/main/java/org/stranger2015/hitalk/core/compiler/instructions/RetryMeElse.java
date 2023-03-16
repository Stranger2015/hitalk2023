package org.stranger2015.hitalk.core.compiler.instructions;

public class RetryMeElse implements Instruction {

	/**
	 * See the WAM tutorial for explanation on the execute functionality of each instruction.
	 *
	 * @param runtime
	 */
	public void execute(PrologRuntime runtime){ 
		runtime.retryChoicePoint();
		runtime.setHBtoH();
		runtime.increaseP();
	}
	
	public String toString(){ return "retry_me_else";	}
}