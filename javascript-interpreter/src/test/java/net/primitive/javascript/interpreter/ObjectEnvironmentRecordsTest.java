package net.primitive.javascript.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.Undefined;

import org.junit.Test;

public class ObjectEnvironmentRecordsTest {

	@Test
	public void should_return_binding_object() {
		ScriptableObject bindingObject = new ScriptableObject();
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, true);
		assertSame(bindingObject, environmentRecords.implicitThisValue());
	}

	@Test
	public void should_return_undefined() {
		ScriptableObject bindingObject = new ScriptableObject();
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, false);
		assertSame(Undefined.Value, environmentRecords.implicitThisValue());
	}

	@Test
	public void should_have_object_binding_value() {
		ScriptableObject bindingObject = new ScriptableObject();
		bindingObject.put("name", "value", false);
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, false);
		assertTrue(environmentRecords.hasBinding("name"));
	}

	@Test
	public void should_not_have_object_binding_value() {
		ScriptableObject bindingObject = new ScriptableObject();
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, false);
		assertFalse(environmentRecords.hasBinding("name"));
	}

	@Test
	public void should_create_mutable_binding_value() {
		ScriptableObject bindingObject = new ScriptableObject();
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, false);
		environmentRecords.createMutableBinding("name", false);

		PropertyDescriptor propertyDescriptor = bindingObject
				.getOwnProperty("name");
		assertNotNull(propertyDescriptor);
		assertEquals(Undefined.Value, propertyDescriptor.getValue());
		assertFalse(propertyDescriptor.isConfigurable());
	}

	@Test
	public void should_set_mutable_binding_value() {
		ScriptableObject bindingObject = new ScriptableObject();
		ObjectEnvironmentRecords environmentRecords = new ObjectEnvironmentRecords(
				bindingObject, false);
		environmentRecords.createMutableBinding("name", false);
		environmentRecords.setMutableBinding("name", "value", false);
		PropertyDescriptor propertyDescriptor = bindingObject
				.getOwnProperty("name");
		assertNotNull(propertyDescriptor);
		assertEquals("value", propertyDescriptor.getValue());
	}
}
