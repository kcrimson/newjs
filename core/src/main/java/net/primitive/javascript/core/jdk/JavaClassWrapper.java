package net.primitive.javascript.core.jdk;

import static net.primitive.javascript.core.Reference.getValue;
import static net.primitive.javascript.core.jdk.JavaHost.unwrap;
import static org.apache.commons.beanutils.ConstructorUtils.invokeConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.primitive.javascript.core.EcmaScriptException;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.ReferenceErrorException;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

/**
 * 
 * @author jpalka@gmail.com
 * 
 */
public class JavaClassWrapper extends ScriptableObject implements Function {

	private final Class<?> javaClass;

	public JavaClassWrapper(Class<?> javaClass) {
		super();
		this.javaClass = javaClass;
	}

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {
		return construct(scope, args);
	}

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		// unwrap arguments
		Object[] convertedArgs = new Object[actualParameters.length];
		for (int i = 0; i < actualParameters.length; i++) {
			convertedArgs[i] = unwrap(getValue((actualParameters[i])));
		}
		return convertedArgs;
	}

	@Override
	public Scope getScope() {
		return null;
	}

	@Override
	public Scriptable construct(Scope scope, Object[] args) {

		try {
			Object object = invokeConstructor(javaClass, args);
			return new JavaObjectWrapper(object);
		} catch (NoSuchMethodException e) {
			throw new ReferenceErrorException("no constructor found");
		} catch (IllegalAccessException e) {
			throw new EcmaScriptException("illegal access to Java object constructor");
		} catch (InvocationTargetException e) {
			throw new EcmaScriptException("illegal access to Java object constructor");
		} catch (InstantiationException e) {
			throw new EcmaScriptException("illegal access to Java object constructor");
		}
	}

	@Override
	public Object hasInstance(Object lvar) {
		return null;
	}

	@Override
	public List<String> getParameterList() {
		return null;
	}

}