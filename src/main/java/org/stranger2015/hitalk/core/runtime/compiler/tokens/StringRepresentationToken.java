package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class StringRepresentationToken implements CompileToken {
	private String value;
	
	public String getValue(){ return value; }
	public StringRepresentationToken(String value){ this.value = value; }	
	
	public String toString(){
		return "<String representation: " + value + ">";
	}
}