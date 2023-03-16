package org.stranger2015.hitalk.core.compiler.tokens;

import static org.stranger2015.hitalk.core.runtime.compiler.WAMTokenizer.varRegisterToString;

public
record ArgumentVariable(int primeRegister, int argumentRegister, String name) implements CompileToken {
	public
	String toString () {
		return "<A%d = %s = %s>".formatted(argumentRegister + 1, varRegisterToString(primeRegister), name);
	}
}
