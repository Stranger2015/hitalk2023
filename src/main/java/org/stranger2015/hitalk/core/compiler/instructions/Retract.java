package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import java.util.HashMap;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.REGISTERS;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.CON;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.STR;

public class Retract implements Instruction {

	private final CellAddress addr = new CellAddress(REGISTERS.ordinal(),0,0);

	public void execute(PrologRuntime runtime){  
		String functor = "";
		MemoryCell strCell = runtime.getCell(addr);
		if(strCell.getType() == CON){
			functor = "%s/0".formatted(strCell.getFunctor());
		} else if(strCell.getType() == STR){
			MemoryCell fnCell = runtime.getCell(strCell.getPointerDomain(), strCell.getPointerFrame(), strCell.getPointerIndex());
			functor = "%s/%d".formatted(fnCell.getFunctor(), fnCell.getArgCount());
		}
		
		StringBuilder r = new StringBuilder();
		runtime.cellToPrologString(addr, r, new HashMap <>());
		runtime.getCodeBase().removeFact(functor, r.toString());
		runtime.increaseP();
	}
	
	public String toString(){ return "retract";	}
}
