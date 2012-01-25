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

import static net.primitive.javascript.core.Convertions.toObject;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scriptable;

public class ObjectReference extends Reference {

	private final Object base;

	public ObjectReference(Object base, String referencedName) {
		super(referencedName);
		this.base = base;
	}

	@Override
	public Object getBase() {
		return base;
	}

	@Override
	public Object getValue() {
		return getBindingObject().get(getReferencedName());
	}

	@Override
	public void setValue(Object value) {
		getBindingObject().put(getReferencedName(), value);
	}

	private Scriptable getBindingObject() {
		if (base instanceof Scriptable) {
			return (Scriptable) base;
		} else {
			return (Scriptable) toObject(base);
		}
	}

	@Override
	public boolean isPropertyReference() {
		return base instanceof Scriptable || hasPrimitiveBase();
	}

	private boolean hasPrimitiveBase() {
		return base instanceof String || base instanceof Number
				|| base instanceof Boolean;
	}

}