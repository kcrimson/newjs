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

/**
 * A Reference is a resolved name binding.
 * 
 * 
 */
public abstract class Reference {

	private final String referencedName;

	public Reference(String referencedName) {
		super();
		this.referencedName = referencedName;
	}

	/**
	 * @return the referencedName
	 */
	public String getReferencedName() {
		return referencedName;
	}

	public boolean isUnresolvableReference() {
		return getBase() == Undefined.Value;
	}

	/**
	 * @return the base
	 */
	public abstract Object getBase();

	public abstract boolean isPropertyReference();

	public abstract Object getValue();

	public abstract void setValue(Object value);

	public static Object getValue(Object object) {
		if (object instanceof Reference) {
			Reference ref = (Reference) object;
			if (ref.isUnresolvableReference()) {
				throw new ReferenceErrorException();
			}

			return ref.getValue();
		}
		return object;
	}

	public static void putValue(Object object, Object value) {
		if (!(object instanceof Reference)) {
			throw new ReferenceErrorException();
		}

		Reference reference = (Reference) object;
		reference.setValue(value);
	}

}
