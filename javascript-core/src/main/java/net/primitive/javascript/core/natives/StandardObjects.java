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
package net.primitive.javascript.core.natives;

import net.primitive.javascript.core.JSObject;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

public class StandardObjects {

	public static Scriptable init() {

		ScriptableObject standardObjects = new ScriptableObject();

		Scriptable objectPrototype = new JSObject();

		Scriptable object = new JSObject();
		object.setPrototype(objectPrototype);

		PropertyDescriptor descriptor = new PropertyDescriptor(standardObjects)
				.isWriteable(true).isEnumerable(false).isConfigurable(true);
		descriptor.setValue(object);

		standardObjects.defineOwnProperty("Object", descriptor, true);

		return standardObjects;
	}

}