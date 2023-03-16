package org.stranger2015.hitalk.core.compiler.tokens;

public class ConstantToken implements CompileToken {
	private final int argument;
	private final String name;
	
	public ConstantToken(String name, int argument){
		this.name = name;
		this.argument = argument;
	}
	
	public String getName(){ return name; }
	public int getArgument(){ return argument; }
	
	public String toString(){ return "<constant %s>".formatted(name); }
}

