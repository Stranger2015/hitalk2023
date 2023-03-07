package org.stranger2015.hitalk.core.compiler.instructions;

public class TryMe implements Instruction{
	private final int arity;
	
	public TryMe(int arity){
		this.arity = arity; 
	}
	
	public void execute(PrologRuntime runtime){
		runtime.newChoicePoint(arity);
		runtime.setHBtoH();
		runtime.increaseP();
	}
	
	public int getArity(){ return arity; }
	public String toString(){ return "try_me " + arity;	}
}
