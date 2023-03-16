package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.Term;
import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

public class GetConstant implements Instruction {
	private final int register;
	private final String name;

	public GetConstant(int register, String name){
		this.register = register;
		this.name = name; 
	}
	@Override
	public void execute( PrologRuntime runtime){
		CellAddress d =deref(register);
		Term m = (Term) runtime.getCell(d);
		boolean fail = false;
		if(m.getType() == MemoryCell.ETypeMemoryCells.REF){
			m.convertToConstantCell(name);
			runtime.trail(d);
		} else if(m.getType() != MemoryCell.ETypeMemoryCells.CON || !m.getFunctor().equals(name)) {
			fail = true;
		}
		if(fail) {
			runtime.backtrack();
		}
		else {
			runtime.increaseP();
		}
	}

	private
	CellAddress deref ( int register ) {
//		this.register = register;
		return null;
	}

	public String toString(){ return "get_constant %s X%d".formatted(name, register + 1); }

	/**
	 * See the WAM tutorial for explanation on the execute functionality of each instruction.
	 *
	 * @param runtime
	 */

}