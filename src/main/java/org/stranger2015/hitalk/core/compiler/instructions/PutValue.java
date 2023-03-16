package org.stranger2015.hitalk.core.compiler.instructions;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class PutValue implements Instruction {
	private final int primeRegister;
	private final int argumentRegister;
	
	public PutValue(int primeRegister, int argumentRegister){
		this.primeRegister = primeRegister;
		this.argumentRegister = argumentRegister;
	}
	
	public void execute(PrologRuntime runtime){
		runtime.setRegister(argumentRegister, primeRegister < 0
				? runtime.getStackVariable(primeRegisterToStackIndex(primeRegister))
				: runtime.getRegisterCell(primeRegister));
		runtime.increaseP();
	}

	public String toString(){ return "put_value %s A%d".formatted(
			varRegisterToString(primeRegister),
			argumentRegister + 1); }
}
