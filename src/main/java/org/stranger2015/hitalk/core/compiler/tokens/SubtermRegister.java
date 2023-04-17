package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

/**
 *
 */
public final class SubtermRegister extends CompilerToken {
	private final int register;

	/**
	 * @param register
	 */
	public SubtermRegister(int register){
		this.register = register;
	}
	
	@Contract(pure = true)
	public int getRegister(){ return register; }

	/**
	 * @return
	 */
	public String toString(){ return "<%s>".formatted(varRegisterToString(register));}
}
