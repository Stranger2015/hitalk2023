package org.stranger2015.hitalk.core.runtime.compiler.tokens;

import org.stranger2015.hitalk.core.compiler.tokens.CompileToken;

public class AllocateToken implements CompileToken {
	private int nrOfVariables;
	
	public AllocateToken(int nrOfVariables){
		this.nrOfVariables = nrOfVariables;
	}
	
	public int getNrOfVariables(){ return nrOfVariables; }
		
	public String toString(){ return "<allocate>"; }
}
