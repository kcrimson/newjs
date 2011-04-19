package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;

public class ObjectEnvironmentRecords implements EnvironmentRecords {

	private final Scriptable bindingObject;

	private final boolean provideThis;

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
	public void createMutableBinding(String name, boolean configValue) {
		if (!bindingObject.hasProperty(name)) {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
					bindingObject).isConfigurable(configValue)
					.isWriteable(true).isEnumerable(true);
			bindingObject.defineOwnProperty(name, propertyDescriptor, false);
		}
	}

	@Override
	public void setMutableBinding(String name, Object value) {
		bindingObject.put(name, value);
	}

	@Override
	public Object getBindingValue(String name) {
		return bindingObject.get(name);
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

}
