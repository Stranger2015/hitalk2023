package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.Term;
import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

import static org.stranger2015.hitalk.core.HitalkInterpreter.deref;

public class GetConstant implements Instruction {
	private int register; 
	private String name;

	public GetConstant(int register, String name){
		this.register = register;
		this.name = name; 
	}


	public void execute( PrologRuntime runtime){
		CellAddress d =deref(PrologRuntime.REGISTERS, 0, register);
		Term m = runtime.getCell(d);
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

	public String toString(){ return "get_constant " + name + " " + "X" + (register+1); }

	/**
	 * See the WAM tutorial for explanation on the execute functionality of each instruction.
	 *
	 * @param runtime
	 */
	@Override
	public
	void execute ( PrologRuntime runtime ) {

	}
}