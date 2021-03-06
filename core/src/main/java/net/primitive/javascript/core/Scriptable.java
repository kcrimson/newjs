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

import java.util.Enumeration;
import java.util.Map;

/**
 * This interface defines JavaScript object internal properties, according to
 * ECMA-262 8.6.2. This interface has to be implemented by all native and host
 * objects.
 * 
 * @author kcrimson@bitbucket.org
 */

public interface Scriptable {

	Scriptable getPrototype();

	void setPrototype(Scriptable prototype);

	String getClassname();

	boolean isExtensible();

	boolean hasProperty(String propertyName);

	Object get(String propertyName);

	PropertyDescriptor getOwnProperty(String propertyName);

	PropertyDescriptor getProperty(String propertyName);

	boolean canPut(String propertyName);

	void put(String propertyName, Object value);

	boolean defineOwnProperty(String propertyName, PropertyDescriptor propertyDescriptor, boolean failureHandling);

	boolean delete(String propertyName, boolean failureHandling);

	Object getDefaultValue(Class<?> hint);

	/**
	 * Returns enumeration of property names whose [[Enumerable]] attribute is
	 * true
	 * 
	 * @return
	 */
	Enumeration<String> enumeration();

	Map<String, PropertyDescriptor> getOwnProperties();

	void setExtensible(boolean b);

}