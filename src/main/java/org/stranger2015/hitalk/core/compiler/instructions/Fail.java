package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class Fail implements Instruction {
	
	public void execute( PrologRuntime runtime){
		runtime.fail();
	}
	
	public String toString(){ return "fail"; }
}
