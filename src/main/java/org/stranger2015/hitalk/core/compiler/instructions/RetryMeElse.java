package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.instructions.wam.Instruction;

public class RetryMeElse implements Instruction {
	 
	public void execute(PrologRuntime runtime){ 
		runtime.retryChoicePoint();
		runtime.setHBtoH();
		runtime.increaseP();
	}
	
	public String toString(){ return "retry_me";	}

    /**
     * See the WAM tutorial for explanation on the execute functionality of each instruction.
     *
     * @param runtime
     */
    @Override
    public
    void execute ( org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime runtime ) {

    }
}