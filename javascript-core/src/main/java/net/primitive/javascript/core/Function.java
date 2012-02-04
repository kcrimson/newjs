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
// API class

package net.primitive.javascript.core;

import java.util.List;

/**
 * This is interface that all functions in JavaScript must implement. The
 * interface provides for calling functions and constructors.
 * 
 * @see org.mozilla.javascript.Scriptable
 * @author Norris Boyd
 */

public interface Function extends Scriptable, Callable, Constructor {

	Object hasInstance(Object lvar);

	List<String> getParameterList();

}
