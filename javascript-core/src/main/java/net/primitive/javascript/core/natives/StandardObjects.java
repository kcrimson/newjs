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
package net.primitive.javascript.core.natives;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

import org.apache.commons.lang3.StringUtils;

/**
 * Standard built-in objects available in ECMAScript.
 * 
 * @author jpalka@gmail.com
 * 
 */
public final class StandardObjects {

	private Scriptable objectPrototype;
	private JSObject objectConstructor;
	private ScriptableObject arrayPrototype;
	private JSArray arrayConstructor;
	private ScriptableObject jsonConstructor;

	private StandardObjects(Scriptable globalObject) {
		defineObject(globalObject);

		defineArray(globalObject);

		defineJSON(globalObject);
	}

	private void defineJSON(Scriptable globalObject) {
		jsonConstructor = new ScriptableObject();
		jsonConstructor.setPrototype(objectPrototype);

		defineFunction(jsonConstructor, "stringify", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSON.stringify(scope, args);
			}
		});

		defineFunction(jsonConstructor, "parse", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSON.parse(scope, args);
			}
		});

		PropertyDescriptor descriptor = new PropertyDescriptor(globalObject).isWriteable(true).isEnumerable(false).isConfigurable(true);
		descriptor.setValue(jsonConstructor);

		globalObject.defineOwnProperty("JSON", descriptor, true);

	}

	public static StandardObjects createStandardObjects(Scriptable globalObject) {
		StandardObjects standardObjects = new StandardObjects(globalObject);
		return standardObjects;
	}

	public Scriptable newObject() {
		ScriptableObject newObject = new ScriptableObject("Object");
		newObject.setPrototype(objectPrototype);
		return newObject;
	}

	public Scriptable newArray() {
		ScriptableObject newArray = new ScriptableObject("Array") {

			@Override
			public boolean defineOwnProperty(String propertyName, PropertyDescriptor desc, boolean failureHandling) {
				PropertyDescriptor lenDesc = getOwnProperty("length");
				if (lenDesc != null) {
					int oldLen = Convertions.toUInt32(lenDesc.getValue());

					// if ("length".equals(propertyName)) {
					//
					// }

					if (StringUtils.isNumeric(propertyName)) {
						int index = Convertions.toUInt32(propertyName);
						if (index >= oldLen && !lenDesc.isWriteable()) {
							throw new IllegalArgumentException("za duzy ten index w stosunku");
						}

						super.defineOwnProperty(propertyName, desc, false);

						if (index >= oldLen) {
							lenDesc.setValue(index + 1);
							super.defineOwnProperty(propertyName, lenDesc, false);
						}

						return true;

					}
				}

				return super.defineOwnProperty(propertyName, desc, failureHandling);
			}
		};
		newArray.setPrototype(arrayPrototype);
		newArray.put("length", 0);
		return newArray;
	}

	private void defineArray(Scriptable globalObject) {

		arrayPrototype = new ScriptableObject();
		arrayPrototype.setPrototype(objectPrototype);

		defineFunction(arrayPrototype, "pop", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSArray.pop(thisObj, args);
			}
		});

		defineFunction(arrayPrototype, "push", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSArray.push(thisObj, args);
			}
		});

		arrayConstructor = new JSArray(this);

		PropertyDescriptor descriptor = new PropertyDescriptor(globalObject).isWriteable(true).isEnumerable(false).isConfigurable(true);
		descriptor.setValue(arrayConstructor);

		globalObject.defineOwnProperty("Array", descriptor, true);

	}

	private void defineObject(Scriptable globalObject) {
		objectPrototype = new ScriptableObject();
		defineFunction(objectPrototype, "toString", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.toString(thisObj, args);
			}
		});

		objectConstructor = new JSObject(this);
		objectConstructor.setPrototype(objectPrototype);

		defineFunction(objectConstructor, "getPrototypeOf", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.getPrototypeOf(thisObj, args);
			}
		});

		defineFunction(objectConstructor, "getOwnPropertyDescriptor", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.getOwnPropertyDescriptor(thisObj, args);
			}
		});

		defineFunction(objectConstructor, "isExtensible", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.isExtensible(thisObj, args);
			}
		});

		defineFunction(objectConstructor, "seal", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.seal(thisObj, args);
			}
		});

		defineFunction(objectConstructor, "freeze", new AbstractCallable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return JSObject.freeze(thisObj, args);
			}
		});

		PropertyDescriptor descriptor = new PropertyDescriptor(globalObject).isWriteable(true).isEnumerable(false).isConfigurable(true);
		descriptor.setValue(objectConstructor);

		globalObject.defineOwnProperty("Object", descriptor, true);
	}

	private static void defineFunction(Scriptable object, String propertyName, AbstractCallable callable) {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(object).isWriteable(false).isEnumerable(false).isConfigurable(false);
		propertyDescriptor.setValue(callable);

		object.defineOwnProperty(propertyName, propertyDescriptor, false);
	}

	public Scriptable getObjectPrototype() {
		return objectPrototype;
	}

	public JSObject getObjectConstructor() {
		return objectConstructor;
	}

	public ScriptableObject getArrayPrototype() {
		return arrayPrototype;
	}

	public JSArray getArrayConstructor() {
		return arrayConstructor;
	}

	public ScriptableObject getJsonConstructor() {
		return jsonConstructor;
	}

}