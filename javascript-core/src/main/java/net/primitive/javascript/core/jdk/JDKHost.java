package net.primitive.javascript.core.jdk;

import java.lang.reflect.Method;

import net.primitive.javascript.core.Scriptable;

public class JDKHost {

	// wraps java object
	public static Scriptable wrapJavaObject(Object object) {
		Method[] methods = object.getClass().getMethods();

		return null;
	}
}
