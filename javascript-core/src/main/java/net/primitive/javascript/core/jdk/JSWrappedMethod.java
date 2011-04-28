package net.primitive.javascript.core.jdk;

import java.lang.reflect.Method;

import net.primitive.javascript.core.Callable;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

public class JSWrappedMethod extends ScriptableObject implements Callable {

	private final Method method;

	public JSWrappedMethod(Method method) {
		this.method = method;
	}

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {
		
		//method.invoke(thisObj, args);
		
		return null;
	}

}
