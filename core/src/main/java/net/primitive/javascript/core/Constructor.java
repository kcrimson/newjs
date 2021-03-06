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
 * 
 * @author jpalka@gmail.com
 * 
 */
public interface Constructor {
	/**
	 * Call the function as a constructor.
	 * 
	 * This method is invoked by the runtime in order to satisfy a use of the
	 * JavaScript <code>new</code> operator. This method is expected to create a
	 * new object and return it.
	 * 
	 * @param scope
	 *            an enclosing scope of the caller except when the function is
	 *            called from a closure.
	 * @param args
	 *            the array of arguments
	 * 
	 * @return the allocated object
	 */
	Scriptable construct(Scope scope, Object[] args);

}
