package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;
import static org.stranger2015.hitalk.core.runtime.PrologRuntime.REGISTERS;
import static org.stranger2015.hitalk.core.runtime.PrologRuntime.STACK;

public class CallVariable implements Instruction {
	private final int primeRegister;
	
	public CallVariable(int primeRegister){
		this.primeRegister = primeRegister; 
	}
	
	private final CellAddress address = new CellAddress();

	public void execute( PrologRuntime runtime){
		if(primeRegister < 0){
			address.copyFrom(runtime.deref(STACK, runtime.getE(), primeRegisterToStackIndex(primeRegister)));
		} else {
			address.copyFrom(runtime.deref(REGISTERS, 0, primeRegister));
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

	/**
	 * @return
	 */
	public String toString(){ return "call %s".formatted(varRegisterToString(primeRegister)); }
	public CellAddress getVariableAddress( PrologRuntime<?> runtime){
		if(primeRegister < 0){
			return (runtime.deref(STACK, runtime.getE(), primeRegisterToStackIndex(primeRegister)));
		} else {
			return (runtime.deref(REGISTERS, 0, primeRegister));
		}
	}
}