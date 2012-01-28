package net.primitive.javascript.core.natives;

import net.primitive.javascript.core.Constructor;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

public class JSError extends ScriptableObject implements Constructor {

	public JSError() {
		super();
	}

	@Override
	public Scriptable construct(Scope scope, Object[] args) {
		return null;
	}

}
