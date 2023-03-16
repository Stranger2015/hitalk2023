package org.stranger2015.hitalk.core.compiler.tokens;

public
record NumberToken(double number, int argument) implements CompileToken {
	public
	String toString () {
		return "<number %s>".formatted(number);
	}

	public static
	class EndOfHead implements CompileToken {
		public String toString(){ return "End of Head Tokens" ; }
	}
}
