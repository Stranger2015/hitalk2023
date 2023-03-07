package org.stranger2015.hitalk.core.compiler.tokens;

public class SubtermRegister implements CompileToken {
	private int register;
	
	public SubtermRegister(int register){
		this.register = register;
	}
	
	public int getRegister(){ return register; }
	
	public String toString(){ return "<" + WAMTokenizer.varRegisterToString(register) + ">";}
}
