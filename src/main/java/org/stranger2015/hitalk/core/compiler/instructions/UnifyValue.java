package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer;
import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.*;

public class UnifyValue implements Instruction {
	private final CellAddress register = new CellAddress();
	
	public UnifyValue(int register){
		if(register < 0) {
			this.register.set(STACK.ordinal(),0, WAMTokenizer.primeRegisterToStackIndex(register));
		}
		else {
			this.register.set(REGISTERS.ordinal(), 0, register);
		}
	}
	
	public void execute(PrologRuntime runtime) {
		if(register.getDomain()== STACK.ordinal()) {
			register.setFrame(runtime.getE());
		}
		boolean fail = false;
		if(runtime.isInWriteMode()) {
			runtime.getNewHeapCell().copyFrom(runtime.getCell(register));
		}
		else {
			fail = !runtime.unify(register.getDomain(),
					register.getFrame(),
					register.getIndex(),
					PrologRuntime.EMemoryTypes.HEAP.ordinal(),
					0,
					runtime.getS());
		}
		runtime.increaseS();
		if(fail) {
			runtime.backtrack();
		}
		else {
			runtime.increaseP();
		}
	}
	
	public String toString(){ return "unify_value %s".formatted(register); }
}