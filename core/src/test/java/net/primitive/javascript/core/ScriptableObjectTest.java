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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ScriptableObjectTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void should_put_property() {

		BaseScriptableObject scriptableObject = new ScriptableObject();

		scriptableObject.put("obj", "obj");

		PropertyDescriptor property = scriptableObject.getProperty("obj");

		assertEquals("obj", property.getValue());
		assertTrue(property.isConfigurable());
		assertTrue(property.isEnumerable());
		assertTrue(property.isWriteable());
	}

	@Test
	public void should_not_put_property_on_not_extensible_object() {
		BaseScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.setExtensible(false);

		expectedException.expect(TypeErrorException.class);

		scriptableObject.put("obj", "obj");

		PropertyDescriptor property = scriptableObject.getProperty("obj");

		assertNull(property);
	}

	@Test
	public void should_throw_type_error_when_put_property_on_not_extensible_object() {
		BaseScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.setExtensible(false);

		expectedException.expect(TypeErrorException.class);

		try {
			scriptableObject.put("obj", "obj");
		} finally {
			PropertyDescriptor property = scriptableObject.getProperty("obj");

			assertNull(property);
		}
	}

	@Test
	public void should_overwrite_property_with_data_descriptor() {
		BaseScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.put("obj", "value0");
		scriptableObject.put("obj", "value1");

		PropertyDescriptor property = scriptableObject.getProperty("obj");
		assertEquals("value1", property.getValue());
	}

	@Test
	public void should_put_property_in_prototype_chain() {
		ScriptableObject prototype = new ScriptableObject();
		prototype.put("name", "value");

		BaseScriptableObject object = new ScriptableObject();
		object.setPrototype(prototype);

		object.put("name", "changed");

		assertEquals("value", prototype.get("name"));
		assertEquals("changed", object.get("name"));
	}

	@Test
	public void should_get_property_in_prototype_chain() {
		ScriptableObject prototype = new ScriptableObject();
		prototype.put("name", "value");

		BaseScriptableObject object = new ScriptableObject();
		object.setPrototype(prototype);

		assertEquals("value", object.get("name"));
	}
}
