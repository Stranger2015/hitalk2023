package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

public class Allocate implements Instruction {
    private final int nrOfVariables;

    public Allocate(int nrOfVariables){
        this.nrOfVariables = nrOfVariables;
    }

    public void execute( PrologRuntime runtime) {
        runtime.newEnvironment(nrOfVariables);
        runtime.increaseP();
    }

    public String toString(){ return "allocate %d".formatted(nrOfVariables);
    }
}

