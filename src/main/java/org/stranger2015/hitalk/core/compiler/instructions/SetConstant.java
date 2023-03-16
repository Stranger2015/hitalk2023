package org.stranger2015.hitalk.core.compiler.instructions;

public class SetConstant implements Instruction {
	private final String name;

	public SetConstant(String name){
		this.name = name;
	}

	public void execute(PrologRuntime runtime){
		runtime.getNewHeapCell().convertToConstantCell(name);
		runtime.increaseP();
	}

	public String toString(){ return "set_constant %s".formatted(name); }
}