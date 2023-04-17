package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.Objects;

/**
 *
 */
public
final
class ArgumentVariable extends CompilerToken {
	private final int primeRegister;
	private final int argumentRegister;
	private final String name;

	/**
	 * @param primeRegister
	 * @param argumentRegister
	 * @param name
	 */
	public
	ArgumentVariable ( int primeRegister, int argumentRegister, String name ) {
		this.primeRegister = primeRegister;
		this.argumentRegister = argumentRegister;
		this.name = name;
	}

	/**
	 * @return
	 */
	public
	String toString () {
		return "<A%d = %s = %s>".formatted(argumentRegister + 1, varRegisterToString(primeRegister), name);
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	int primeRegister () {
		return primeRegister;
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	int argumentRegister () {
		return argumentRegister;
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	String name () {
		return name;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Contract(value = "null -> false", pure = true)
	@Override
	public
	boolean equals ( Object obj ) {
		boolean result;
		if (obj == this) {
			result = true;
		}
		else if (obj == null || obj.getClass() != this.getClass()) {
			result = false;
		}
		else {
			var that = (ArgumentVariable) obj;
			result = this.primeRegister == that.primeRegister &&
					this.argumentRegister == that.argumentRegister &&
					Objects.equals(this.name, that.name);
		}
		return result;
	}

	@Override
	public
	int hashCode () {
		return Objects.hash(primeRegister, argumentRegister, name);
	}

}
