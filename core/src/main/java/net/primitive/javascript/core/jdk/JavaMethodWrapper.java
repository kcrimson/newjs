package net.primitive.javascript.core.jdk;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.MethodUtils;

import net.primitive.javascript.core.Callable;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

/**
 * 
 * @author jpalka@gmail.com
 * 
 */
public class JavaMethodWrapper extends ScriptableObject implements Callable {

	private final Object javaObject;
	private final String methodName;

	public JavaMethodWrapper(Object javaObject, String methodName) {
		this.javaObject = javaObject;
		this.methodName = methodName;
	}

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {

		Object object = null;
		try {
			object = MethodUtils.invokeMethod(javaObject, methodName, args);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JavaHost.wrap(object);
	}

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		Object[] convertedArgs = new Object[actualParameters.length];
		for (int i = 0; i < actualParameters.length; i++) {
			convertedArgs[i] = JavaHost.unwrap((actualParameters[i]));
		}
		return convertedArgs;
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return null;
	}

}
