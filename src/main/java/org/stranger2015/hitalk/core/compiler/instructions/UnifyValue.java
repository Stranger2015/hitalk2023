package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;

public class UnifyValue implements Instruction {
	private final CellAddress register = new CellAddress();
	
	public UnifyValue(int register){
		if(register < 0) {
			this.register.set(STACK.ordinal(),0, primeRegisterToStackIndex(register));
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
					HEAP.ordinal(),
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