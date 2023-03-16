package org.stranger2015.hitalk.core.compiler.tokens;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class SubtermRegister implements CompileToken {
	private final int register;
	
	public SubtermRegister(int register){
		this.register = register;
	}
	
	public int getRegister(){ return register; }
	
	public String toString(){ return "<%s>".formatted(varRegisterToString(register));}
}
