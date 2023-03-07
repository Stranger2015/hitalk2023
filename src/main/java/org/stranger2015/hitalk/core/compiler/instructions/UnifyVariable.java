package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer;

public class UnifyVariable implements Instruction {
	private final int register;
	
	public UnifyVariable(int register){
		this.register = register; 
	}
	
	private final CellAddress h = new CellAddress(EHEAP.ordinal(), 0, 0);
	public void execute(PrologRuntime runtime) {
		MemoryCell m;
		if(runtime.isInWriteMode()){
			h.setIndex(runtime.getH().getIndex());
			m = runtime.getNewHeapCell();
			m.convertToRefCell(h);
		} else {
			m = runtime.getHeapCell(runtime.getS());
		}
		if(register<0) {
			runtime.setStackVariable(WAMTokenizer.primeRegisterToStackIndex(register), m);
		}
		else {
			runtime.setRegister(register, m);
		}
		runtime.increaseS();
		runtime.increaseP();
	}
	
	public String toString(){ return "unify_variable %s".formatted(WAMTokenizer.varRegisterToString(register)); }
}