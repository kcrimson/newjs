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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import net.primitive.javascript.core.BaseScriptableObject;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.annotations.JSFunction;

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
		System.out.println("root package: "+propertyName);
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
		// TODO should return empty
		return null;
	}

	@Override
	public Map<String, PropertyDescriptor> getOwnProperties() {
		// TODO should return empty
		return null;
	}

}