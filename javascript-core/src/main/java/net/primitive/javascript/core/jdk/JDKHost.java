/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
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

import java.lang.reflect.Method;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.annotations.JSFunction;

public final class JDKHost {

	private JDKHost() {

	}

	// wraps java object
	public static Scriptable wrapJavaObject(Object object) {
		Method[] methods = object.getClass().getMethods();

		Scriptable jsObject = new ScriptableObject();
		for (Method method : methods) {
			if (method.isAnnotationPresent(JSFunction.class)) {
				JSWrappedMethod jsFunc = new JSWrappedMethod(object, method);
				PropertyDescriptor ownProperty = new PropertyDescriptor(jsObject);
				ownProperty.setValue(jsFunc);
				jsObject.defineOwnProperty(method.getName(), ownProperty, false);
			}
		}

		return jsObject;
	}

}
