package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.Objects;

/**
 *
 */
public
final
class ListToken extends CompilerToken {
	private final int register;

	/**
	 * @param register
	 */
	public
	ListToken ( int register ) {
		this.register = register;
	}

	/**
	 * @return
	 */
	public
	String toString () {
		return "<list, X%d>".formatted(register + 1);
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	int register () {
		return register;
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
			var that = (ListToken) obj;
			result = this.register == that.register;
		}

		return result;
	}

	@Override
	public
	int hashCode () {
		return Objects.hash(register);
	}

}
