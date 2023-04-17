package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import java.util.HashMap;

import static org.stranger2015.hitalk.core.compiler.PrologToWAMCompiler.compileStringFact;
import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;
import static org.stranger2015.hitalk.core.runtime.PrologRuntime.REGISTERS;

public class Assert implements Instruction {
	private final CellAddress addr = new CellAddress(REGISTERS,0,0);

	@Override
	public void execute( PrologRuntime runtime){
		MemoryCell strCell = runtime.getCell(addr);
		if(strCell.getType()!= CON && strCell.getType()!= REF) {
			runtime.backtrack();
		}
		else {
			StringBuilder r = new StringBuilder();
			runtime.cellToPrologString(addr, r, new HashMap <>());
			r.append(".");
			String functor = "";
			if(strCell.getType() == CON){
				functor = "%s/0".formatted(strCell.getFunctor());
			} else if(strCell.getType() == STR){
				MemoryCell fnCell = runtime.getCell(
						strCell.getPointerDomain(),
						strCell.getPointerFrame(),
						strCell.getPointerIndex());

				functor = "%s/%d".formatted(fnCell.getFunctor(), fnCell.getArgCount());
			}
			assert compileStringFact(functor, r.toString()) != null;
			runtime.getCodeBase().addCodeClause(compileStringFact(functor, r.toString()));
			runtime.moveToContinuationInstruction(); // continue
		}
	}

	/**
	 * @return
	 */
	public String toString(){ return "assert";	}
}