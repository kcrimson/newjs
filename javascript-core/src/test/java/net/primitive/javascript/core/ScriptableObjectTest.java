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

		ScriptableObject scriptableObject = new ScriptableObject();

		scriptableObject.put("obj", "obj", false);

		PropertyDescriptor property = scriptableObject.getProperty("obj");

		assertEquals("obj", property.getValue());
		assertTrue(property.isConfigurable());
		assertTrue(property.isEnumerable());
		assertTrue(property.isWriteable());
	}

	@Test
	public void should_not_put_property_on_not_extensible_object() {
		ScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.setExtensible(false);

		scriptableObject.put("obj", "obj", false);

		PropertyDescriptor property = scriptableObject.getProperty("obj");

		assertNull(property);
	}

	@Test
	public void should_throw_type_error_when_put_property_on_not_extensible_object() {
		ScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.setExtensible(false);

		expectedException.expect(TypeErrorException.class);

		try {
			scriptableObject.put("obj", "obj", true);
		} finally {
			PropertyDescriptor property = scriptableObject.getProperty("obj");

			assertNull(property);
		}
	}

	@Test
	public void should_overwrite_property_with_data_descriptor() {
		ScriptableObject scriptableObject = new ScriptableObject();
		scriptableObject.put("obj", "value0", false);
		scriptableObject.put("obj", "value1", false);

		PropertyDescriptor property = scriptableObject.getProperty("obj");
		assertEquals("value1", property.getValue());
	}

	@Test
	public void should_put_property_in_prototype_chain() {
		ScriptableObject prototype = new ScriptableObject();
		prototype.put("name", "value", false);

		ScriptableObject object = new ScriptableObject();
		object.setPrototype(prototype);

		object.put("name", "changed", false);

		assertEquals("value", prototype.get("name"));
		assertEquals("changed", object.get("name"));
	}

	@Test
	public void should_get_property_in_prototype_chain() {
		ScriptableObject prototype = new ScriptableObject();
		prototype.put("name", "value", false);

		ScriptableObject object = new ScriptableObject();
		object.setPrototype(prototype);

		assertEquals("value", object.get("name"));
	}
}
