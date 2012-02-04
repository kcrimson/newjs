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
package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.ReferenceErrorException;
import net.primitive.javascript.core.Undefined;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObjectReferenceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void should_return_value() {
		Object ref = new Object();

		Object object = Reference.getValue(ref);

		Assert.assertSame(ref, object);
	}

	@Test
	public void should_throw_reference_error() {
		Reference ref = new ObjectReference(Undefined.Value, "obj");

		expectedException.expect(ReferenceErrorException.class);

		Reference.getValue(ref);

	}

}
