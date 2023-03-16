package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.CellAddress;
import org.stranger2015.hitalk.core.runtime.MemoryCell;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;
import static org.stranger2015.hitalk.core.runtime.PrologRuntime.*;

public class Arithmetic implements Instruction {
	private boolean fail;
	
	public void execute( PrologRuntime runtime){
		fail = false;
		double number = evaluate(runtime, REGISTERS,0,1); // Evaluate second argument
		if(!fail) {
			bindNumber(runtime,number);
		}
		else {
			runtime.backtrack();
		}
	}
	 
	private double evaluate(PrologRuntime<?> runtime, int domain, int frame, int index){
		CellAddress d = new CellAddress(); // TODO: do different perhaps for speed?
		d.copyFrom(runtime.deref(domain, frame, index));
		MemoryCell m = runtime.getCell(d);
		double r = 1;
		if(m.getType() == NUM){
			return m.getNumber();
		} else if(m.getType() == STR){
			// Go to functor cell
			d.set(m.getPointerDomain(), m.getPointerFrame(), m.getPointerIndex());
			m = runtime.getCell(d);
			String functor = m.getFunctor();
			switch (functor) {
				case "+" -> {
					return evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1) +
							evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 2);
				}
				case "*" -> {
					return evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1) *
							evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 2);
				}
				case "-" -> {
					return evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1) -
							evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 2);
				}
				case "/" -> {
					return evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1) /
							evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 2);
				}
				case "^" -> {
					return Math.pow(evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1),
							evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 2));
				}
				case "" -> {  // Parenthesized formula
					return evaluate(runtime, d.getDomain(), d.getFrame(), d.getIndex() + 1);
				}
				default -> fail = true;
			}
		} else {
			fail = true;
		}

		return r;
	}
	
	private void bindNumber(PrologRuntime<?> runtime, double number){
		CellAddress d = runtime.deref(REGISTERS, 0, 0); // Get the first argument
		MemoryCell m = runtime.getCell(d);
		boolean fail = false;
		if(m.getType() == REF){ // If the first argument is a variable then bind the result to it
			m.convertToNumberCell(number);
			runtime.trail(d);
		} else if(m.getType() != NUM || m.getNumber() != number) {
			fail = true;
		}
		if(fail) {
			runtime.backtrack();
		}
		else runtime.moveToContinuationInstruction();
	}

	public String toString(){return "arithmetic";}
}
