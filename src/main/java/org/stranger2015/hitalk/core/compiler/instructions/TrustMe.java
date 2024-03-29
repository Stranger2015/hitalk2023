package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class TrustMe implements Instruction {

	public
	void execute ( PrologRuntime runtime ) {
		runtime.trustChoicePoint();
		runtime.setHBtoH();
		runtime.increaseP();
	}

	public
	String toString () {
		return "trust_me";
	}
}