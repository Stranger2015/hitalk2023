package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.NUM;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.REF;

public class UnifyNumber implements Instruction {
	private final double number;
	
	public UnifyNumber(double number){
		this.number = number;
	}

	public void execute(PrologRuntime runtime){
		boolean fail = false;
		if(runtime.isInWriteMode()){
			runtime.getNewHeapCell().convertToNumberCell(number);
		} else { 
			CellAddress d = runtime.deref(HEAP.ordinal(), 0,runtime.getS());
			MemoryCell m = runtime.getCell(d);
			if(m.getType() == REF){
				m.convertToNumberCell(number);
				runtime.trail(d);
			} else if((m.getType() != NUM) || (m.getNumber() != number)) {
				fail = true;
			}
			if(!fail) {
				runtime.increaseS();
			}
		}
		if(fail) {
			runtime.backtrack();
		}
		else {
			runtime.increaseP();
		}
	}

	public String toString(){ return "unify_number " + number; }
}