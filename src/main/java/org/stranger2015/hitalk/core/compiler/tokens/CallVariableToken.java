package org.stranger2015.hitalk.core.compiler.tokens;

import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public class CallVariableToken extends CompilerToken {
	private int register;
	
	public CallVariableToken(int register){
		this(ETokenKind.CALL_VARIABLE,-1,-1,"",255);//fixme
		this.register = register;
	}

	public
	CallVariableToken ( ETokenKind type, int i1, int i2, String str, double num ) {
		super(type, i1, i2, str, num);
	}

	public int getRegister(){ return register; }
	
	public String toString(){ return "<call %s>".formatted(varRegisterToString(register));}
}
