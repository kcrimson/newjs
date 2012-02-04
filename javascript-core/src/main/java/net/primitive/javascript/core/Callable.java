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
package net.primitive.javascript.core;

/**
 * Generic notion of callable object that can execute some script-related code
 * upon request with specified values for script scope and this objects.
 */
public interface Callable {
	/**
	 * Perform the call.
	 * 
	 * @param scope
	 *            the scope to use to resolve properties.
	 * @param thisObj
	 *            the JavaScript <code>this</code> object
	 * @param args
	 *            the array of arguments
	 * 
	 * @return the result of the call
	 */
	Object call(Scope scope, Scriptable thisObj, Object[] args);

	/**
	 * 
	 * @param actualParameters
	 * @return
	 */
	Object[] bindParameters(Object[] actualParameters);

	Scope getScope();
}
