package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.REGISTERS;
import static org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime.EMemoryTypes.STACK;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.primeRegisterToStackIndex;
import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class CallVariable implements Instruction {
	private final int primeRegister;
	
	public CallVariable(int primeRegister){
		this.primeRegister = primeRegister; 
	}
	
	private final CellAddress address = new CellAddress();

	public void execute(PrologRuntime runtime){ 
		if(primeRegister < 0){
			address.copyFrom(runtime.deref(STACK.ordinal(), runtime.getE(), primeRegisterToStackIndex(primeRegister)));
		} else {
			address.copyFrom(runtime.deref(REGISTERS.ordinal(), 0, primeRegister));
		}
		MemoryCell strCell = runtime.getCell(address);
		if(strCell.getType() == CON){
			runtime.setCPToNextInstruction();
			runtime.goToClause("%s/0".formatted(strCell.getFunctor()));
		} else if(strCell.getType() == STR){
			runtime.setCPToNextInstruction();
			address.set(strCell.getPointerDomain(), strCell.getPointerFrame(), strCell.getPointerIndex()); // go to FN cell
			MemoryCell fnCell = runtime.getCell(address);
			for(int i = 0; i < fnCell.getArgCount(); i++){
				runtime.setRegister(i, runtime.getCell(address.getDomain(),address.getFrame(),address.getIndex()+1+i));
			}
			runtime.goToClause("%s/%d".formatted(fnCell.getFunctor(), fnCell.getArgCount()));
		} else runtime.backtrack();
	}

	public String toString(){ return "call %s".formatted(varRegisterToString(primeRegister)); }
	public CellAddress getVariableAddress(PrologRuntime runtime){
		if(primeRegister < 0){
			return (runtime.deref(STACK.ordinal(), runtime.getE(), primeRegisterToStackIndex(primeRegister)));
		} else {
			return (runtime.deref(REGISTERS.ordinal(), 0, primeRegister));
		}
	}
}