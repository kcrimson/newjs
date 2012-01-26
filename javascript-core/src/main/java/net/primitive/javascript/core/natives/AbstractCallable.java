package net.primitive.javascript.core.natives;

import net.primitive.javascript.core.Callable;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.ScriptableObject;

public abstract class AbstractCallable extends ScriptableObject implements Callable {

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		return actualParameters;
	}

	@Override
	public Scope getScope() {
		return null;
	}

}
