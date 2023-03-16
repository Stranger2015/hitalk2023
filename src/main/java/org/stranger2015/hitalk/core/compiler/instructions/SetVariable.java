package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class SetVariable implements Instruction {
	private final int register;
	
	public SetVariable(int register){
		this.register = register; 
	}
	
	private final CellAddress h = new CellAddress(HEAP.ordinal(), 0, 0);
	public void execute( PrologRuntime runtime) {
		h.setIndex(runtime.getH().getIndex());
		MemoryCell refCell = runtime.getNewHeapCell();
		refCell.convertToRefCell(h); // refer to itself
		if(register < 0){
			runtime.setStackVariable(primeRegisterToStackIndex(register), refCell);
		} else {
			runtime.setRegister(register, refCell);
		}
		runtime.increaseP();
	}
	
	public String toString(){ return "set_variable " + varRegisterToString(register); }
}
