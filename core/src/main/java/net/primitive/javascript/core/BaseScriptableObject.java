package net.primitive.javascript.core;

import static net.primitive.javascript.core.Convertions.toObject;

/**
 * Base implementation of {@link net.primitive.javascript.core.Scriptable}.
 * Implements prototype chain, but doesn't implement store for members.
 * 
 * @author jpalka@gmail.com
 * 
 */
public abstract class BaseScriptableObject implements Scriptable {

	private static final String PROTOTYPE = "prototype";
	protected final String classname;
	private Scriptable prototype;
	protected boolean extensible = true;

	public BaseScriptableObject(String classname) {
		this.classname = classname;
	}

	public BaseScriptableObject() {
		classname = null;
	}

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
		return classname;
	}

	@Override
	public boolean isExtensible() {
		return extensible;
	}

	@Override
	public Object get(String propertyName) {

		if (PROTOTYPE.equals(propertyName)) {
			return getPrototype();
		}

		PropertyDescriptor descriptor = getProperty(propertyName);
		if (descriptor == null) {
			return Undefined.Value;
		}
		return descriptor.getValue();
	}

	/**
	 * TODO should we stop traversing prototype chain when checking getting
	 * property "prototype"?
	 */
	@Override
	public PropertyDescriptor getProperty(String propertyName) {

		PropertyDescriptor prop = getOwnProperty(propertyName);

		if (prop != null) {
			return prop;
		}

		Scriptable proto = getPrototype();
		if (proto != null) {
			return proto.getProperty(propertyName);
		}

		return null;
	}

	@Override
	public void put(String propertyName, Object value) {

		if (PROTOTYPE.equals(propertyName)) {
			setPrototype(toObject(value));
			return;
		}

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
			defineOwnProperty(propertyName, propertyDescriptor, false);
			// associatedProperties.put(propertyName, propertyDescriptor);
		} else {
			throw new TypeErrorException();
		}

	}

	@Override
	public boolean canPut(String propertyName) {
		return isExtensible();
	}

	@Override
	public boolean hasProperty(String propertyName) {
		return getProperty(propertyName) != null;
	}

	@Override
	public Object getDefaultValue(Class<?> hint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExtensible(boolean b) {
		extensible = b;
	}

	@Override
	public String toString() {
		return "[object Object]";
	}

}