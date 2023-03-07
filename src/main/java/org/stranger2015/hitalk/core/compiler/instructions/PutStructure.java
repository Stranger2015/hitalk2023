package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.*;

public class PutStructure implements Instruction {
	private final int register;
	private final int arity;
	private final String functor;
	
	public PutStructure(int register, String functor, int arity){
		this.register = register;
		this.functor = functor;
		this.arity = arity;
	}
	
	private final CellAddress h = new CellAddress(HEAP.ordinal(),0,0);
	public void execute(PrologRuntime runtime){
		h.setIndex(runtime.getH().getIndex()); 
		runtime.getNewHeapCell().convertToFunctorCell(functor, arity);
		runtime.getRegisterCell(register).convertToStructureCell(h); 
		runtime.increaseP();
	} 
	
	public String toString(){ return "put_structure " + functor + "/" + arity + " X" + (register+1); }
}
