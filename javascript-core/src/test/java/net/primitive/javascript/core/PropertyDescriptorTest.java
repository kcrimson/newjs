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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class PropertyDescriptorTest {

	public class PropertyValueHolder {
		private Object value;
		private boolean setterInvoked = false;
	}

	@Test
	public void test_data_descriptor_set_value() {
		ScriptableObject thisObj = new ScriptableObject();
		PropertyDescriptor descriptor = new PropertyDescriptor(thisObj);

		descriptor.setValue("value");

		assertEquals("value", descriptor.getValue());
	}

	@Test
	public void test_accessor_descriptor_set_value() {
		ScriptableObject thisObj = new ScriptableObject();
		PropertyDescriptor descriptor = new PropertyDescriptor(thisObj);

		final PropertyValueHolder propertyHolder = new PropertyValueHolder();

		Callable getter = new Callable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				return propertyHolder.value;
			}
		};

		Callable setter = new Callable() {

			@Override
			public Object call(Scope scope, Scriptable thisObj, Object[] args) {
				propertyHolder.value = args[0];
				propertyHolder.setterInvoked = true;
				return null;
			}
		};

		descriptor.setGetter(getter);

		descriptor.setSetter(setter);

		descriptor.setValue("value");

		assertTrue(propertyHolder.setterInvoked);
		assertEquals("value", descriptor.getValue());

	}

	@Test
	public void should_empty_descriptor_be_generic() {
		ScriptableObject thisObj = new ScriptableObject();
		PropertyDescriptor descriptor = new PropertyDescriptor(thisObj);
		assertFalse(PropertyDescriptor.isDataDescriptor(descriptor));
		assertFalse(PropertyDescriptor.isAccessorDescriptor(descriptor));
	}

	@Test
	public void should_initialized_be_data_descriptor() {
		ScriptableObject thisObj = new ScriptableObject();
		PropertyDescriptor descriptor = new PropertyDescriptor(thisObj)
				.isWriteable(true);

		descriptor.setValue("value");

		assertTrue(PropertyDescriptor.isDataDescriptor(descriptor));
		assertFalse(PropertyDescriptor.isAccessorDescriptor(descriptor));
	}

	@Test
	public void should_bound_be_accessor_descriptor() {
		ScriptableObject thisObj = new ScriptableObject();
		PropertyDescriptor descriptor = new PropertyDescriptor(thisObj);
		descriptor.setGetter(mock(Callable.class));
		descriptor.setSetter(mock(Callable.class));
		assertFalse(PropertyDescriptor.isDataDescriptor(descriptor));
		assertTrue(PropertyDescriptor.isAccessorDescriptor(descriptor));

	}
}
