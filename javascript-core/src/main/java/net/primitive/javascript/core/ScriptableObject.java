package net.primitive.javascript.core;

import java.util.HashMap;
import java.util.Map;

public class ScriptableObject implements Scriptable {

	private final Map<String, PropertyDescriptor> associatedProperties = new HashMap<String, PropertyDescriptor>();

	private boolean extensible = true;

	private Scriptable prototype;

	@Override
	public Scriptable getPrototype() {
		return prototype;
	}

	@Override
	public void setPrototype(Scriptable prototype) {
		this.prototype = prototype;
	}

	@Override
	public String getClassname() {
		return null;
	}

	@Override
	public boolean isExtensible() {
		return extensible;
	}

	@Override
	public Object get(String propertyName) {
		PropertyDescriptor descriptor = getProperty(propertyName);
		return descriptor.getValue();
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {

		PropertyDescriptor descriptor = associatedProperties.get(propertyName);

		return descriptor;
	}

	@Override
	public PropertyDescriptor getProperty(String propertyName) {

		PropertyDescriptor prop = getOwnProperty(propertyName);

		if (prop != null) {
			return prop;
		}

		if (prototype != null) {
			return prototype.getProperty(propertyName);
		}

		return null;
	}

	@Override
	public void put(String propertyName, Object value, boolean failureHandling) {

		if (canPut(propertyName)) {

			PropertyDescriptor ownDesc = getOwnProperty(propertyName);

			if (PropertyDescriptor.isDataDescriptor(ownDesc)) {
				ownDesc.setValue(value);
				return;
			}

			PropertyDescriptor desc = getProperty(propertyName);

			if (PropertyDescriptor.isAccessorDescriptor(desc)) {
				desc.setValue(value);
				return;
			}

			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(this)
					.isWriteable(true).isEnumerable(true).isConfigurable(true);
			propertyDescriptor.setValue(value);
			associatedProperties.put(propertyName, propertyDescriptor);
		} else if (failureHandling) {
			throw new TypeErrorException();
		}

	}

	@Override
	public boolean canPut(String propertyName) {
		return isExtensible();
	}

	@Override
	public boolean hasProperty(String propertyName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String propertyName, boolean failureHandling) {
		return false;
	}

	@Override
	public Object getDefaultValue(Class<?> hint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean defineOwnProperty(String propertyName,
			PropertyDescriptor desc, boolean failureHandling) {
		PropertyDescriptor current = getOwnProperty(propertyName);

		if (current == null && !extensible) {
			if (failureHandling) {
				throw new TypeErrorException();
			}
			return false;
		}

		if (current == null && extensible) {
			PropertyDescriptor newDesc = desc.cloneDescriptor(this);
			associatedProperties.put(propertyName, newDesc);
			return true;
		}

		if (!desc.isConfigurable() && !desc.isEnumerable()
				&& !desc.isWriteable()) {
			return true;
		}

		return false;
	}

	public void setExtensible(boolean b) {
		extensible = b;
	}

}
