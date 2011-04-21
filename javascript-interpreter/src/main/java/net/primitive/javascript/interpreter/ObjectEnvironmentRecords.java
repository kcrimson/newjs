package net.primitive.javascript.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.utils.FastMap;

public class ObjectEnvironmentRecords implements EnvironmentRecords {

	final Scriptable bindingObject;

	private final boolean provideThis;

	// holds references to binding object properties
	private Map<String, Reference> bindings = new FastMap<String, Reference>();

	public ObjectEnvironmentRecords(Scriptable bindingObject,
			boolean provideThis) {
		super();
		this.bindingObject = bindingObject;
		this.provideThis = provideThis;
	}

	@Override
	public boolean hasBinding(String name) {
		return bindingObject.hasProperty(name);
	}

	@Override
	public Reference createMutableBinding(String name, boolean configValue) {
		PropertyDescriptor propertyDescriptor;
		if (!bindingObject.hasProperty(name)) {
			propertyDescriptor = new PropertyDescriptor(bindingObject)
					.isConfigurable(configValue).isWriteable(true)
					.isEnumerable(true);
			bindingObject.defineOwnProperty(name, propertyDescriptor, false);

		}

		ObjectReference reference = new ObjectReference(bindingObject, name);
		bindings.put(name, reference);
		return reference;
	}

	@Override
	public boolean deleteBinding(String name) {
		return bindingObject.delete(name, false);
	}

	@Override
	public Object implicitThisValue() {
		if (provideThis) {
			return bindingObject;
		}
		return Undefined.Value;
	}

	@Override
	public Reference getBinding(String name) {
		return bindings.get(name);
	}

}
