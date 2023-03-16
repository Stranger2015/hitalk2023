package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.*;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class PutVariable implements Instruction {
	private final int primeRegister;
	private final int argumentRegister;
	
	public PutVariable(int primeRegister, int argumentRegister){
		this.primeRegister = primeRegister;
		perm.setIndex(primeRegisterToStackIndex(primeRegister));
		this.argumentRegister = argumentRegister;
	}
	
	private final CellAddress h = new CellAddress(HEAP.ordinal(),0,0);
	private final CellAddress perm = new CellAddress(STACK.ordinal(),0,0);
	public void execute(PrologRuntime runtime){
		MemoryCell m;
		if(primeRegister < 0){
			perm.setFrame(runtime.getE());
			m = runtime.getCell(perm);
			m.convertToRefCell(perm);
		} else {
			h.setIndex(runtime.getH().getIndex());
			m = runtime.getNewHeapCell();
			m.convertToRefCell(h);
			runtime.setRegister(primeRegister, m);
		}
		runtime.setRegister(argumentRegister, m);
		runtime.increaseP();
	}

	public String toString(){ return "put_variable %s A%d".formatted(varRegisterToString(primeRegister),
			argumentRegister + 1); }
}