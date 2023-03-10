package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class ListToken implements CompileToken {
	private int register;
	
	public ListToken(int register){
		this.register = register;
	}
	
	public int getRegister(){ return register; }
		
	public String toString(){ return "<list, X"+(register+1)+">"; }
}
