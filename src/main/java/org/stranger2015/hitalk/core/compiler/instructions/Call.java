package org.stranger2015.hitalk.core.compiler.instructions;

import org.stranger2015.hitalk.core.runtime.PrologRuntime;

/**
 *
 */
public class Call implements Instruction {
	private final String functor;

	/**
	 * @param functor
	 * @param arity
	 */
	public Call(String functor, int arity){
		this.functor = "%s/%d".formatted(functor, arity);
	}//todo pred indicator
	/**
	 * @param runtime
	 */
	@Override
	public void execute( PrologRuntime runtime){
		runtime.setCPToNextInstruction();
		runtime.setB0();
		runtime.goToClause(functor);
	}

	/**
	 * @return
	 */
	public String toString(){ return "call %s".formatted(functor); }

	/**
	 * @return
	 */
	public String getFunctor(){ return functor; }
}
