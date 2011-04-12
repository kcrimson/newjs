package net.primitive.javascript.core.natives;

import net.primitive.javascript.core.ScriptableObject;

public abstract class JSPrimitveWrapper<T> extends ScriptableObject {

	private final T primitiveValue;

	public JSPrimitveWrapper(T primitiveValue) {
		super();
		this.primitiveValue = primitiveValue;
	}

	/**
	 * @return the primitiveValue
	 */
	public T getPrimitiveValue() {
		return primitiveValue;
	}

}
