package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

public class PutNumber implements Instruction {
	private final int register;
	private final double number;
	
	public PutNumber(int register, double number){
		this.register = register;
		this.number = number; 
	}
	
	//private CellAddress h = new CellAddress(PrologRuntime.HEAP,0,0);
	public void execute(PrologRuntime runtime){
		runtime.getRegisterCell(register).convertToNumberCell(number);
		runtime.increaseP();
	} 
	
	public String toString(){ return "put_number %s X%d".formatted(number, register + 1); }
}
