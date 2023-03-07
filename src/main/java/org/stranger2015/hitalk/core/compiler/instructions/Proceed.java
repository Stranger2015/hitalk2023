package org.stranger2015.hitalk.core.compiler.instructions;

public class Proceed implements Instruction {

	public void execute(PrologRuntime runtime) { 
		runtime.moveToContinuationInstruction();
	}
	
	public String toString(){ return "proceed";	}
}
