package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class NumberToken implements CompileToken {
	private double number; 
	private int argument;
	
	public NumberToken(double number, int argument){
		this.number = number;
		this.argument = argument;
	}
	
	public double getNumber(){ return number; }
	public int getArgument(){ return argument; }
	
	public String toString(){ return "<number " + number + ">"; }
}
