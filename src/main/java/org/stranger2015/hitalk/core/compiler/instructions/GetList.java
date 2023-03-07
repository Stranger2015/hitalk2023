package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

public class GetList implements Instruction {
	private final int register;
	
	public GetList(int register){
		this.register = register; 
	}
	
	private final CellAddress h = new CellAddress(runtime.PrologRuntime.HEAP,0,0);
	public void execute( PrologRuntime runtime){
		CellAddress d = runtime.deref(runtime.PrologRuntime.REGISTERS, 0, register);
		MemoryCell m = runtime.getCell(d);
		if(m.getType() == MemoryCell.ETypeMemoryCells.REF){
			h.setIndex(runtime.getH().getIndex()+1);
			runtime.getNewHeapCell().convertToListCell(h);
			h.setIndex(h.getIndex()-1);
			runtime.bind(d, h);
			runtime.setWriteMode(true);
			runtime.increaseP();
		} else if(m.getType() == MemoryCell.LIS){
			runtime.setS(m.getPointerIndex());
			runtime.setWriteMode(false);
			runtime.increaseP();
		} else runtime.backtrack();
	} 
	
	public String toString(){ return "get_list " + "X" + (register+1); }
}