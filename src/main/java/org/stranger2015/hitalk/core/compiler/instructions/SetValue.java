package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class SetValue implements Instruction {
	private final int register;
	
	public SetValue(int register){
		this.register = register; 
	}
	
	public void execute(PrologRuntime runtime) {
		if(register < 0){
			runtime.getNewHeapCell().copyFrom(runtime.getStackVariable(primeRegisterToStackIndex(register)));
		} else {
			runtime.getNewHeapCell().copyFrom(runtime.getRegisterCell(register));
		}
		runtime.increaseP();
	}
	
	public String toString(){ return "set_value " + varRegisterToString(register); }
}
