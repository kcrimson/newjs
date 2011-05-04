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

package net.primitive.javascript.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;

public class ObjectEnvironmentRecords implements ScopeBindings {

	final Scriptable bindingObject;

	private final boolean provideThis;

	// holds references to binding object properties
	private Map<String, Reference> bindings = new HashMap<String, Reference>();

	public ObjectEnvironmentRecords(Scriptable bindingObject,
			boolean provideThis) {
		super();
		this.bindingObject = bindingObject;
		this.provideThis = provideThis;
	}

	@Override
	public boolean hasBinding(String name) {
		return bindingObject.hasProperty(name);
	}

	@Override
	public Reference createMutableBinding(String name, boolean configValue) {
		PropertyDescriptor propertyDescriptor;
		if (!bindingObject.hasProperty(name)) {
			propertyDescriptor = new PropertyDescriptor(bindingObject)
					.isConfigurable(configValue).isWriteable(true)
					.isEnumerable(true);
			bindingObject.defineOwnProperty(name, propertyDescriptor, false);

		}

		ObjectReference reference = new ObjectReference(bindingObject, name);
		bindings.put(name, reference);
		return reference;
	}

	@Override
	public boolean deleteBinding(String name) {
		return bindingObject.delete(name, false);
	}

	@Override
	public Object implicitThisValue() {
		if (provideThis) {
			return bindingObject;
		}
		return Undefined.Value;
	}

	@Override
	public Reference getBinding(String name) {
		Reference reference = bindings.get(name);
		if (reference == null) {
			reference = createMutableBinding(name, false);
		}
		return reference;
	}

}
