package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.CON;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.REF;

public class UnifyConstant implements Instruction {
	private final CellAddress s = new CellAddress(HEAP.ordinal(), 0, 0);

	private final String name;
	public UnifyConstant(String name){
		this.name = name;
	}

	public void execute(PrologRuntime runtime){
		boolean fail = false;
		if(runtime.isInWriteMode()){
			runtime.getNewHeapCell().convertToConstantCell(name);
		} else {
			CellAddress d = runtime.deref(HEAP.ordinal(), 0, runtime.getS());
			MemoryCell m = runtime.getCell(d);
			if(m.getType() == REF){
				m.convertToConstantCell(name);
				runtime.trail(d);
			} else if(m.getType() != CON || !m.getFunctor().equals(name)) {
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

	public String toString(){ return "unify_constant " + name; }
}