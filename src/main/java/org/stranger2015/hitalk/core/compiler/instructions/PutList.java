package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class PutList implements Instruction {
	private final int register;
	
	public PutList(int register){
		this.register = register; 
	}
	
	public void execute( PrologRuntime runtime){
		runtime.getRegisterCell(register).convertToListCell(runtime.getH());
		runtime.increaseP();
	} 
	
	public String toString(){ return "put_list X%d".formatted(register + 1); }
}