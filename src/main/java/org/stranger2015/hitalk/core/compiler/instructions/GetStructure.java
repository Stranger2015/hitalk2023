package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.*;

public class GetStructure implements Instruction {
	private final int arity;
	private final String functor;
	private final int register;
	
	public GetStructure(int register, String functor, int arity){
		this.register = register;
		this.functor = functor;
		this.arity = arity;
	}
	
	private static final CellAddress h = new CellAddress(HEAP.ordinal(),-1,0);
	public void execute(PrologRuntime runtime){
		CellAddress a = runtime.deref(REGISTERS.ordinal(), -1,register);
		MemoryCell m = runtime.getCell(a);
		boolean fail = false;
		if(m.getType()==MemoryCell.ETypeMemoryCells.REF){
			h.setIndex(runtime.getH().getIndex());
			runtime.getNewHeapCell().convertToStructureCell(runtime.getH());
			runtime.getNewHeapCell().convertToFunctorCell(functor, arity);
			runtime.bind(a, h);
			runtime.setWriteMode(true);
		} else if(m.getType()==MemoryCell.ETypeMemoryCells.STR){
			a.set(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex());
			m = runtime.getCell(a);
			if(m.getType()==MemoryCell.ETypeMemoryCells.FN && m.getFunctor().equals(functor) && m.getArgCount()==arity){
				runtime.setS(a.getIndex()+1);
				runtime.setWriteMode(false);
			} else {
				fail = true;
			}
		} else {
			fail = true;
		}
		if(fail) {
			runtime.backtrack();
		}
		else {
			runtime.increaseP();
		}
	} 
	
	public String toString(){ return "get_structure %s/%d X%d".formatted(functor, arity, register + 1); }
}
