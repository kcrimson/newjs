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
package net.primitive.javascript.core.natives;

import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.transformValues;
import static net.primitive.javascript.core.Convertions.toObject;
import static net.primitive.javascript.core.Reference.getValue;

import java.util.Map;

import net.primitive.javascript.core.Operators;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.TypeErrorException;
import net.primitive.javascript.core.Types;

import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class JSON extends ScriptableObject {

	public JSON() {
		super("JSON");
	}

	public static Object stringify(Scope scope, Object[] args) {
		Scriptable object = toObject(extractArgument(args));
		Map<String, PropertyDescriptor> ownProperties = object
				.getOwnProperties();

		Map<String, Object> map = transformValues(
				filterValues(ownProperties,
						new Predicate<PropertyDescriptor>() {

							@Override
							public boolean apply(PropertyDescriptor desc) {
								return desc.isEnumerable();
							}
						}), new Function<PropertyDescriptor, Object>() {

					@Override
					public Object apply(PropertyDescriptor desc) {
						return desc.getValue();
					}
				});

		return new JSONObject(map).toString();
	}

	public static Object parse(Scope scope, Object[] args) {
		// String text = Convertions.toString((extractArgument(args)));
		//
		// JSONObject json = new JSONObject(text);
		throw new UnsupportedOperationException("not implemented yet");
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
