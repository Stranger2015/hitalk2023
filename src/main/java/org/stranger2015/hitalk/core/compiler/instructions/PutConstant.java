package org.stranger2015.hitalk.core.compiler.instructions;

public class PutConstant implements Instruction {
	private final int register;
	private final String name;
	
	public PutConstant(int register, String name){
		this.register = register;
		this.name = name; 
	}
	
	public void execute(PrologRuntime runtime){
		runtime.getRegisterCell(register).convertToConstantCell(name);  
		runtime.increaseP();
	} 
	
	public String toString(){ return "put_constant %s A%d".formatted(name, register + 1); }
}