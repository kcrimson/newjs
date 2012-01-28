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

import static net.primitive.javascript.core.Convertions.toObject;
import static net.primitive.javascript.core.Reference.getValue;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.primitive.javascript.core.Constructor;
import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Operators;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.TypeErrorException;
import net.primitive.javascript.core.Types;
import net.primitive.javascript.core.Undefined;

/**
 * Implementation of ECMAScript Object constructor
 * 
 * @author jpalka@gmail.com
 * 
 */
public class JSObject extends ScriptableObject implements Function, Constructor {

	private final StandardObjects standardObjects;

	public JSObject(StandardObjects standardObjects) {
		super("Function");
		this.standardObjects = standardObjects;
	}

	/**
	 * @see ECMA262#15.2.1.1
	 */
	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {
		Object value = extractArgument(args);

		if (value == null || Undefined.Value.equals(value)) {
			return construct(scope, args);
		}

		return Convertions.toString(value);
	}

	/**
	 * @see ECMA262#15.2.2.1
	 */
	@Override
	public Scriptable construct(Scope scope, Object[] args) {
		Object value = extractArgument(args);

		if (value == null || Undefined.Value.equals(value)) {
			return standardObjects.newObject();
		}
		return null;
	}

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		return null;
	}

	@Override
	public Scope getScope() {
		return null;
	}

	@Override
	public Object hasInstance(Object lvar) {
		return null;
	}

	@Override
	public List<String> getParameterList() {
		return null;
	}

	/*
	 * Implementations of properties of the Object Constructor
	 */

	/**
	 * 
	 * @param thisObj
	 * @param obj
	 * @return
	 * @see ECMA262#15.2.3.2
	 */
	public static Object getPrototypeOf(Scriptable thisObj, Object[] args) {
		// TODO is this a right place to extract value from reference, shouldn't
		// it be extracted in other place?
		Object obj = extractArgument(args);

		return toObject(obj).getPrototype();
	}

	/**
	 * 
	 * @param thisObj
	 * @param args
	 * @return
	 * @see ECMA262#15.2.3.8
	 */
	public static Object seal(Scriptable thisObj, Object[] args) {
		Object obj = extractArgument(args);
		Scriptable scriptable = toObject(obj);
		Map<String, PropertyDescriptor> ownProperties = scriptable
				.getOwnProperties();
		for (Entry<String, PropertyDescriptor> desc : ownProperties.entrySet()) {
			desc.getValue().setConfigurable(false);
			scriptable.defineOwnProperty(desc.getKey(), desc.getValue(), true);
		}
		scriptable.setExtensible(false);
		return scriptable;
	}

	/**
	 * 
	 * @param thisObj
	 * @param args
	 * @return
	 * @see ECMA262#15.2.3.3
	 */
	public static Object getOwnPropertyDescriptor(Scriptable thisObj,
			Object[] args) {
		Object obj = extractArgument(args);
		String name = Convertions.toString(getValue(args[1]));
		Scriptable scriptable = toObject(obj);
		return Convertions.fromPropertyDescriptor(scriptable
				.getOwnProperty(name));
	}

	/**
	 * 
	 * @param thisObj
	 * @param args
	 * @return
	 * @see ECMA262#15.2.3.9
	 */
	public static Object freeze(Scriptable thisObj, Object[] args) {
		Object obj = extractArgument(args);
		Scriptable scriptable = toObject(obj);
		Map<String, PropertyDescriptor> ownProperties = scriptable
				.getOwnProperties();
		for (Entry<String, PropertyDescriptor> desc : ownProperties.entrySet()) {
			desc.getValue().setConfigurable(false);
			desc.getValue().setWriteable(false);
			scriptable.defineOwnProperty(desc.getKey(), desc.getValue(), true);
		}
		scriptable.setExtensible(false);
		return scriptable;
	}

	public static Object isExtensible(Scriptable thisObj, Object[] args) {
		Object obj = extractArgument(args);
		return toObject(obj).isExtensible();
	}

	/**
	 * 
	 * @param thisObj
	 * @param args
	 * @return
	 */
	public static Object keys(Scriptable thisObj, Object[] args) {
		Object obj = extractArgument(args);
		return toObject(obj).isExtensible();
	}

	/*
	 * Implementations of object prototype properties
	 */

	public static Object toString(Scriptable thisObj, Object[] args) {
		return "[object " + toObject(thisObj).getClassname() + "]";
	}

	/**
	 * Returns first argument or null if no arguments at all
	 * 
	 * @param args
	 * @param value
	 * @return
	 * @throws TypeErrorException
	 *             - when first argument is not {@link Scriptable}
	 */
	private static Object extractArgument(Object[] args) {
		Object firstArg = null;
		if (args != null && args.length > 0) {
			firstArg = getValue(args[0]);
		}

		if (Types.Object.equals(Operators.TypeOf.operator(firstArg))) {
			return firstArg;
		}

		throw new TypeErrorException();
	}

}
