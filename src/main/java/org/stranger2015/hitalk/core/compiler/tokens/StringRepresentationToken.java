package org.stranger2015.hitalk.core.compiler.tokens;

import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.Objects;

public
final
class StringRepresentationToken extends CompilerToken {
	private final String value;

	/**
	 * @param value
	 */
	public
	StringRepresentationToken ( String value ) {
		this.value = value;
	}

	/**
	 * @param type
	 * @param i1
	 * @param i2
	 * @param str
	 * @param num
	 */
	public
	StringRepresentationToken ( ETokenKind type,
								int i1,
								int i2,
								String str,
								double num ) {

		super(type, i1, i2, str, num);
		value=str;//fixme
	}

	public
	String toString () {
		return "<String representation: %s>".formatted(value);
	}

	public
	String value () {
		return value;
	}

	@Override
	public
	boolean equals ( Object obj ) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (StringRepresentationToken) obj;

		return Objects.equals(this.value, that.value);
	}

	@Override
	public
	int hashCode () {
		return Objects.hash(value);
	}

}