package org.stranger2015.hitalk.core.compiler.instructions;

public class Deallocate implements Instruction {

	public void execute(PrologRuntime runtime) { 
		runtime.popEnvironment();
	}
	
	public String toString(){ return "deallocate";	}
}
