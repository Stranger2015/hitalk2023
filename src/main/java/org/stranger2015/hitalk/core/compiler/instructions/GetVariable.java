package org.stranger2015.hitalk.core.compiler.instructions;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class GetVariable implements Instruction {
	private final int primeRegister;
	private final int argumentRegister;
	
	public GetVariable(int primeRegister, int argumentRegister){
		this.primeRegister = primeRegister;
		this.argumentRegister = argumentRegister;
	}
	
	public void execute(PrologRuntime runtime){
		if(primeRegister < 0){
			runtime.setStackVariable(primeRegisterToStackIndex(primeRegister), runtime.getRegisterCell(argumentRegister));
		} else {
			runtime.setRegister(primeRegister, runtime.getRegisterCell(argumentRegister));
		}
		runtime.increaseP();
	}

	public String toString(){ return "get_variable %s A%d".formatted(
			varRegisterToString(primeRegister),
			argumentRegister + 1); }
}
