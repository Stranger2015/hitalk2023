package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.NUM;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.REF;

public class GetNumber implements Instruction {
	private final int register;
	private final double number;
	
	public GetNumber(int register, double number){
		this.register = register;
		this.number = number; 
	}
	
	public void execute(PrologRuntime runtime){
		CellAddress d = runtime.deref(REGISTERS.ordinal(), 0, register);
		MemoryCell m = runtime.getCell(d);
		boolean fail = false;
		if(m.getType() == REF){
			m.convertToNumberCell(number);
			runtime.trail(d);
		} else if(m.getType() != NUM || m.getNumber() != number) {
			fail = true;
		}
		if(fail) {
			runtime.backtrack();
		}
		else {
			runtime.increaseP();
		}
	} 
	
	public String toString(){ return "get_number %s X%d".formatted(number, register + 1); }
}
