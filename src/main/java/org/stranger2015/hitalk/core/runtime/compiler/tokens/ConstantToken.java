package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class ConstantToken implements CompileToken {
	private int argument;
	private String name;
	
	public ConstantToken(String name, int argument){
		this.name = name;
		this.argument = argument;
	}
	
	public String getName(){ return name; }
	public int getArgument(){ return argument; }
	
	public String toString(){ return "<constant " + name + ">"; }
}

