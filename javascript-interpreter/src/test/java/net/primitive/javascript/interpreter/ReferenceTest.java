package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.JSObject;
import net.primitive.javascript.core.ReferenceErrorException;
import net.primitive.javascript.core.Undefined;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReferenceTest {

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
		Reference ref = new Reference(Undefined.Value, "obj");

		expectedException.expect(ReferenceErrorException.class);

		Object object = Reference.getValue(ref);

	}

	@Test
	public void should_return_unreferenced_value() {
		JSObject jsObject = new JSObject();
		Reference ref = new Reference(jsObject, "obj");

		Object object = Reference.getValue(ref);

		Assert.assertSame(jsObject, object);
	}

}
