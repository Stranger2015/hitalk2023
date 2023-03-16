package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public class Cut implements Instruction {

	@Override
	public void execute( PrologRuntime runtime) {
		// The choice point at the moment of the call of the current procedure becomes the top choicepoint
		// B0 is this choice point
		runtime.setB0AsCurrentChoicePoint();
		runtime.tidyTrail();
		runtime.increaseP();
	}

	/**
	 * @return
	 */
	public String toString(){ return "cut";	}
}