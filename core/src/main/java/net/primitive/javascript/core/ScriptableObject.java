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

import static com.google.common.collect.Iterators.asEnumeration;
import static com.google.common.collect.Maps.filterEntries;
import static java.util.Collections.unmodifiableMap;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;

/**
 * 
 * @author jpalka@gmail.com
 * 
 */
public class ScriptableObject extends BaseScriptableObject {

	private final Map<String, PropertyDescriptor> associatedProperties = new HashMap<String, PropertyDescriptor>();

	public ScriptableObject() {
		super();
	}

	public ScriptableObject(String classname) {
		super(classname);
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {

		PropertyDescriptor descriptor = associatedProperties.get(propertyName);

		return descriptor;
	}

	@Override
	public boolean delete(String propertyName, boolean failureHandling) {

		PropertyDescriptor propertyDescriptor = associatedProperties
				.remove(propertyName);

		return propertyDescriptor != null;
	}

	@Override
	public boolean defineOwnProperty(String propertyName,
			PropertyDescriptor desc, boolean failureHandling) {
		PropertyDescriptor current = getOwnProperty(propertyName);

		if (current == null && !extensible) {
			if (failureHandling) {
				throw new TypeErrorException();
			}
			return false;
		}

		if (current == null && extensible) {
			PropertyDescriptor newDesc = desc.cloneDescriptor(this);
			associatedProperties.put(propertyName, newDesc);
			return true;
		}

		if (!desc.isConfigurable() && !desc.isEnumerable()
				&& !desc.isWriteable()) {
			return true;
		}

		return false;
	}

	@Override
	public Enumeration<String> enumeration() {
		Map<String, PropertyDescriptor> enumerableProperties = filterEntries(
				associatedProperties,
				new Predicate<Map.Entry<String, PropertyDescriptor>>() {

					@Override
					public boolean apply(Entry<String, PropertyDescriptor> entry) {
						return entry.getValue().isEnumerable();
					}
				});
		return asEnumeration(enumerableProperties.keySet().iterator());
	}

	@Override
	public Map<String, PropertyDescriptor> getOwnProperties() {
		return unmodifiableMap(associatedProperties);
	}

}
