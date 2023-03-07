package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import compiler.Compiler;
import compiler.WAMTokenizer;
import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class ArgumentVariable implements CompileToken {
	private int primeRegister, argumentRegister;
	private String name; 
	
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
