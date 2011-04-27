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
package net.primitive.javascript.core;

public final class Types {

	public final static String Object = "object";

	public final static String Function = "function";

	public final static String Number = "number";

	public final static String String = "string";

	public final static String Boolean = "boolean";

	public final static String Undefined = "undefined";

	private Types() {

	}

	public static boolean isConstructor(Object obj) {
		return obj instanceof Constructor;
	}
}
