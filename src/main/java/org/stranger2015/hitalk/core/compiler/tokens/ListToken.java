package org.stranger2015.hitalk.core.compiler.tokens;

public
record ListToken(int register) implements CompileToken {
	public
	String toString () {
		return "<list, X%d>".formatted(register + 1);
	}
}
