package org.stranger2015.hitalk.core.compiler.tokens;

public class CallVariableToken implements CompileToken {
	private int register;
	
	public CallVariableToken(int register){
		this.register = register;
	}
	
	public int getRegister(){ return register; }
	
	public String toString(){ return "<call " + WAMTokenizer.varRegisterToString(register) + ">";}
}
