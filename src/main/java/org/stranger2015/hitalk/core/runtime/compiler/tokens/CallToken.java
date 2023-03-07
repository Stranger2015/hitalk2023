package org.stranger2015.hitalk.core.runtime.compiler.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class CallToken implements CompileToken {
	private String functor;
	private int arity;
	
	public CallToken(String functor, int arity){
		this.functor = functor;
		this.arity = arity;
	}
	
	public int getArity(){ return arity; }
	public String getFunctor(){ return functor; }
	
	public String toString(){ return "<call " + functor + "/" + arity + ">"; }
}
