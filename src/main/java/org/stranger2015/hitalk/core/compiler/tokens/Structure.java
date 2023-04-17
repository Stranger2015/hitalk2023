package org.stranger2015.hitalk.core.compiler.tokens;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.Objects;

/**
 *
 */
public
final
class Structure extends CompilerToken {
	private final int register;
	private final String functor;
	private final int arity;

	/**
	 * @param register
	 * @param functor
	 * @param arity
	 */
	public
	Structure ( int register, String functor, int arity ) {
		this.register = register;
		this.functor = functor;
		this.arity = arity;
	}


	/**
	 * @return
	 */
	public
	String toString () {
		return "<X%d = %s/%d>".formatted(register + 1, functor, arity);
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
	 * @return
	 */
	@Contract(pure = true)
	public
	String functor () {
		return functor;
	}

	/**
	 * @return
	 */
	@Contract(pure = true)
	public
	int arity () {
		return arity;
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
			var that = (Structure) obj;
			result = this.register == that.register &&
					Objects.equals(this.functor, that.functor) &&
					this.arity == that.arity;
		}

		return result;
	}

	@Override
	public
	int hashCode () {
		return Objects.hash(register, functor, arity);
	}

}
