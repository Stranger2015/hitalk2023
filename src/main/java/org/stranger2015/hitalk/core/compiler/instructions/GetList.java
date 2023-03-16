package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;

public class GetList implements Instruction {
	private final int register;
	
	public GetList(int register){
		this.register = register; 
	}
	
	private final CellAddress h = new CellAddress(HEAP.ordinal(),0,0);
	public void execute( PrologRuntime runtime){
		CellAddress d = runtime.deref(REGISTERS.ordinal(), 0, register);
		MemoryCell m = runtime.getCell(d);
		if(m.getType() == REF){
			h.setIndex(runtime.getH().getIndex()+1);
			runtime.getNewHeapCell().convertToListCell(h);
			h.setIndex(h.getIndex()-1);
			runtime.bind(d, h);
			runtime.setWriteMode(true);
			runtime.increaseP();
		} else if(m.getType() == LIS){
			runtime.setS(m.getPointerIndex());
			runtime.setWriteMode(false);
			runtime.increaseP();
		} else runtime.backtrack();
	} 
	
	public String toString(){ return "get_list X%d".formatted(register + 1); }
}