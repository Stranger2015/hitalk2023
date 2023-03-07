package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import compiler.Compiler;
import compiler.WAMTokenizer;
import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class CallVariableToken implements CompileToken {
	private int register;
	
	public CallVariableToken(int register){
		this.register = register;
	}
	
	public int getRegister(){ return register; }
	
	public String toString(){ return "<call " + WAMTokenizer.varRegisterToString(register) + ">";}
}
