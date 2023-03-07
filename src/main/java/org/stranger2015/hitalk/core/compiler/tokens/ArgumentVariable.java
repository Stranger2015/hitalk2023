package org.stranger2015.hitalk.core.compiler.tokens;

public class ArgumentVariable implements CompileToken {
	private final int primeRegister;
	private final int argumentRegister;
	private final String name;
	
	public ArgumentVariable(int primeRegister, int argumentRegister, String name){
		this.primeRegister = primeRegister;
		this.argumentRegister = argumentRegister;
		this.name = name;
	}
	
	public int getPrimeRegister(){ return primeRegister; }
	public int getArgumentRegister(){ return argumentRegister; }
	public String getName(){ return name; }
	
	public String toString(){ return "<A" + (argumentRegister+1) + " = " + WAMTokenizer.varRegisterToString(primeRegister) + " = " + name + ">"; }
}
