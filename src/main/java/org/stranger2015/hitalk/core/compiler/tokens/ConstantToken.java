package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

/**
 *
 */
public final
class ConstantToken extends CompilerToken {
	private final int argument;
	private final String name;
	
	public ConstantToken(String name, int argument){
		this.name = name;
		this.argument = argument;
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public String getName(){ return name; }

	/**
	 * @return
	 */
	@Contract(pure = true)
	public int getArgument(){ return argument; }

	/**
	 * @return
	 */
	public String toString(){ return "<constant %s>".formatted(name); }
}

