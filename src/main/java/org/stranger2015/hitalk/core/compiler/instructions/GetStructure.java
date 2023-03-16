package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;

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
		CellAddress a = deref(REGISTERS.ordinal(), -1,register);
		MemoryCell m = getCell(a);
		boolean fail = false;
		if(m.getType()== REF){
			h.setIndex(getH().getIndex());
			getNewHeapCell().convertToStructureCell(getH());
			getNewHeapCell().convertToFunctorCell(functor, arity);
			bind(a, h);
			setWriteMode(true);
		} else if(m.getType()== STR){
			a.set(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex());
			m = getCell(a);
			if(m.getType()== FN && m.getFunctor().equals(functor) && m.getArgCount()==arity){
				setS(a.getIndex()+1);
				setWriteMode(false);
			} else {
				fail = true;
			}
		} else {
			fail = true;
		}
		if(fail) {
			backtrack();
		}
		else {
			increaseP();
		}
	}

	public String toString(){ return "get_structure %s/%d X%d".formatted(functor, arity, register + 1); }
}
