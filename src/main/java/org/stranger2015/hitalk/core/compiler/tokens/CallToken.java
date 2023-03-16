package org.stranger2015.hitalk.core.compiler.tokens;

import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

public class CallToken extends CompilerToken {
	private final String functor;
	private final int arity;
	
	public CallToken(String functor, int arity){
		super();
		this.functor = functor;
		this.arity = arity;
	}
	
	public int getArity(){ return arity; }
	public String getFunctor(){ return functor; }
	
	public String toString(){ return "<call %s/%d>".formatted(functor, arity); }
}
