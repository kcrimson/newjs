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

import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Undefined;

public class DeclarativeEnvironmentRecords implements ScopeBindings {

	private Map<String, Reference> bindings = new HashMap<String, Reference>();

	@Override
	public boolean hasBinding(String name) {
		return bindings.containsKey(name);
	}

	@Override
	public Reference createMutableBinding(String name, boolean d) {
		Reference ref = new DeclarativeReference(name);
		bindings.put(name, ref);
		return ref;
	}

	@Override
	public boolean deleteBinding(String name) {
		return false;
	}

	@Override
	public Object implicitThisValue() {
		return Undefined.Value;
	}

	public void createImmutableBinding(String name) {

	}

	public void initializeImmutableBinding(String name, Object value) {

	}

	private class DeclarativeReference extends Reference {

		private Object value = Undefined.Value;

		public DeclarativeReference(String referencedName) {
			super(referencedName);
		}

		@Override
		public Object getBase() {
			return DeclarativeEnvironmentRecords.this;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public boolean isPropertyReference() {
			return false;
		}

	}

	@Override
	public Reference getBinding(String name) {
		return bindings.get(name);
	}

}
