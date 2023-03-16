package org.stranger2015.hitalk.core.compiler.tokens;

public
record Structure(int register, String functor, int arity) implements CompileToken {


	public
	String toString () {
		return "<X%d = %s/%d>".formatted(register + 1, functor, arity);
	}
}
