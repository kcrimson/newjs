/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.core.jdk;

import static java.util.Collections.emptyMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import net.primitive.javascript.core.BaseScriptableObject;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.annotations.JSFunction;
import net.primitive.javascript.core.natives.JSPrimitveWrapper;

/**
 * Wrapper methods for Java objects, classes and methods
 * 
 * @author jpalka@gmail.com
 * 
 */
public final class JavaHost extends BaseScriptableObject {

	public JavaHost() {

	}

	/**
	 * Unwraps Java object from ECMAScript object
	 * 
	 * @return
	 */
	public static Object unwrap(Object object) {
		if (object == null || Undefined.Value == object
				|| object instanceof String || object instanceof Boolean
				|| object instanceof Number) {
			return object;
		}

		if (object instanceof JSPrimitveWrapper) {
			return ((JSPrimitveWrapper<?>) object).getPrimitiveValue();
		}

		if (object instanceof JavaObjectWrapper) {
			return ((JavaObjectWrapper) object).getJavaObject();
		}

		return object;
	}

	public static Object wrap(Object object) {
		if (object == null || Undefined.Value == object
				|| object instanceof String || object instanceof Boolean
				|| object instanceof Number || object instanceof Scriptable) {
			return object;
		}
		return new JavaObjectWrapper(object);
	}

	/**
	 * Will try to wrap Java class, into ECMAScript constructor object.
	 * 
	 * @param jClass
	 * @return
	 */
	public static Scriptable wrapJavaClass(Class<?> jClass) {

		// lookup for constructors
		Constructor<?>[] constructors = jClass.getConstructors();

		return null;
	}

	// wraps java object
	public static Scriptable wrapJavaObject(Object jObject) {
		Method[] methods = jObject.getClass().getMethods();

		Scriptable jsObject = new ScriptableObject();
		for (Method method : methods) {
			if (method.isAnnotationPresent(JSFunction.class)) {
				JSWrappedMethod jsFunc = new JSWrappedMethod(jObject, method);
				PropertyDescriptor ownProperty = new PropertyDescriptor(
						jsObject);
				ownProperty.setValue(jsFunc);
				jsObject.defineOwnProperty(method.getName(), ownProperty, false);
			}
		}

		return jsObject;
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {
		PropertyDescriptor descriptor = new PropertyDescriptor(this)
				.isConfigurable(false).isEnumerable(false).isWriteable(false);
		descriptor.setValue(new JavaPackageWrapper(propertyName));
		return descriptor;
	}

	@Override
	public boolean defineOwnProperty(String propertyName,
			PropertyDescriptor propertyDescriptor, boolean failureHandling) {
		// no-op, JavaHost will not define new properties, since this object
		// will be not extensible
		return false;
	}

	@Override
	public boolean delete(String propertyName, boolean failureHandling) {
		// no-op, JavaHost will not delete existing java packages
		return false;
	}

	@Override
	public Enumeration<String> enumeration() {
		return null;
	}

	@Override
	public Map<String, PropertyDescriptor> getOwnProperties() {
		return emptyMap();
	}

}