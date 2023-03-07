package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

public class SetNumber implements Instruction {
	private final double number;
	public SetNumber(double number){
		this.number = number;
	} 
	public void execute(PrologRuntime runtime){
		runtime.getNewHeapCell().convertToNumberCell(number);
		runtime.increaseP();
	}

	public String toString(){ return "set_number %s".formatted(number); }
}
