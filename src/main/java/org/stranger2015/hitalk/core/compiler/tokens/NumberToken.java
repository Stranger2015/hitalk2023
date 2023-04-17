package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.Objects;

/**
 *
 */
public
final
class NumberToken extends CompilerToken {
	private final double number;
	private final int argument;

	/**
	 * @param number
	 * @param argument
	 */
	public
	NumberToken ( double number, int argument ) {
		this.number = number;
		this.argument = argument;
	}

	/**
	 * @return
	 */
	public
	String toString () {
		return "<number %s>".formatted(number);
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	double number () {
		return number;
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	int argument () {
		return argument;
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
			var that = (NumberToken) obj;
			result = Double.doubleToLongBits(this.number) == Double.doubleToLongBits(that.number) &&
					this.argument == that.argument;
		}

		return result;
	}

	/**
	 * @return
	 */
	@Override
	public
	int hashCode () {
		return Objects.hash(number, argument);
	}


	/**
	 *
	 */
	public static final
	class EndOfHead extends CompilerToken {
		public
		String toString () {
			return "End of Head Tokens";
		}
	}
}
