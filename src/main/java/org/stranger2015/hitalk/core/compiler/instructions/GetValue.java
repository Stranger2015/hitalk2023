package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.REGISTERS;
import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryType.STACK;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class GetValue implements Instruction {
	private final CellAddress primeRegister = new CellAddress();
	private final CellAddress argumentRegister = new CellAddress(REGISTERS.ordinal(),0,0);
	
	public GetValue(int primeRegister, int argumentRegister){
		if(primeRegister < 0){
			this.primeRegister.set(STACK.ordinal(),0, primeRegisterToStackIndex(primeRegister));
		} else {
			this.primeRegister.set(REGISTERS.ordinal(), 0, primeRegister);
		}
		this.argumentRegister.setIndex(argumentRegister);
	}
	
	public void execute(PrologRuntime runtime){
		if(primeRegister.getDomain() == STACK.ordinal()) // Refers to stack so update with latest environment
		{
			this.primeRegister.setFrame(runtime.getE());
		}
		if(runtime.unify(primeRegister.getDomain(),primeRegister.getFrame(),primeRegister.getIndex(), 
				         argumentRegister.getDomain(),argumentRegister.getFrame(),argumentRegister.getIndex())) {
			runtime.increaseP();
		}
		else {
			runtime.backtrack();
		}
	}

	public String toString(){ return "get_value %s A%d".formatted(varRegisterToString(
			primeRegister.getIndex()),
			argumentRegister.getIndex() + 1); }
}
