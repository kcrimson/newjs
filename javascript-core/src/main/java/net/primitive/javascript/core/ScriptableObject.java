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

import static com.google.common.collect.Iterators.asEnumeration;
import static com.google.common.collect.Maps.filterEntries;
import static java.util.Collections.unmodifiableMap;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;

public class ScriptableObject implements Scriptable {

	private static final String PROTOTYPE = "prototype";

	private final Map<String, PropertyDescriptor> associatedProperties = new HashMap<String, PropertyDescriptor>();

	private boolean extensible = true;

	@Override
	public Scriptable getPrototype() {
		PropertyDescriptor propertyDescriptor = associatedProperties.get(PROTOTYPE);
		if (propertyDescriptor != null) {
			return (Scriptable) propertyDescriptor.getValue();
		}
		return null;
	}

	@Override
	public void setPrototype(Scriptable prototype) {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(this);
		propertyDescriptor.setValue(prototype);
		defineOwnProperty(PROTOTYPE, propertyDescriptor, false);
	}

	@Override
	public String getClassname() {
		return null;
	}

	@Override
	public boolean isExtensible() {
		return extensible;
	}

	@Override
	public Object get(String propertyName) {
		PropertyDescriptor descriptor = getProperty(propertyName);
		if (descriptor == null) {
			return Undefined.Value;
		}
		return descriptor.getValue();
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {

		PropertyDescriptor descriptor = associatedProperties.get(propertyName);

		return descriptor;
	}

	/**
	 * TODO should we stop traversing prototype chain when checking getting
	 * property "prototype"?
	 */
	@Override
	public PropertyDescriptor getProperty(String propertyName) {

		PropertyDescriptor prop = getOwnProperty(propertyName);

		if (prop != null) {
			return prop;
		}

		Scriptable prototype = getPrototype();
		if (prototype != null) {
			return prototype.getProperty(propertyName);
		}

		return null;
	}

	@Override
	public void put(String propertyName, Object value) {

		if (canPut(propertyName)) {

			PropertyDescriptor ownDesc = getOwnProperty(propertyName);

			if (PropertyDescriptor.isDataDescriptor(ownDesc)) {
				ownDesc.setValue(value);
				return;
			}

			PropertyDescriptor desc = getProperty(propertyName);

			if (PropertyDescriptor.isAccessorDescriptor(desc)) {
				desc.setValue(value);
				return;
			}

			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(this).isWriteable(true).isEnumerable(true).isConfigurable(true);
			propertyDescriptor.setValue(value);
			associatedProperties.put(propertyName, propertyDescriptor);
		} else {
			throw new TypeErrorException();
		}

	}

	@Override
	public boolean canPut(String propertyName) {
		return isExtensible();
	}

	@Override
	public boolean hasProperty(String propertyName) {
		return getProperty(propertyName) != null;
	}

	@Override
	public boolean delete(String propertyName, boolean failureHandling) {

		PropertyDescriptor propertyDescriptor = associatedProperties.remove(propertyName);

		return propertyDescriptor != null;
	}

	@Override
	public Object getDefaultValue(Class<?> hint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean defineOwnProperty(String propertyName, PropertyDescriptor desc, boolean failureHandling) {
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

		if (!desc.isConfigurable() && !desc.isEnumerable() && !desc.isWriteable()) {
			return true;
		}

		return false;
	}

	@Override
	public void setExtensible(boolean b) {
		extensible = b;
	}

	@Override
	public Enumeration<String> enumeration() {
		Map<String, PropertyDescriptor> enumerableProperties = filterEntries(associatedProperties, new Predicate<Map.Entry<String, PropertyDescriptor>>() {

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
