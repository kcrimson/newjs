package net.primitive.javascript.core;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PropertyDescriptor {

	private static final Object[] EMPTY_ARGS = new Object[] {};

	private final Scriptable thisObject;

	private boolean enumerable;

	private boolean configurable;

	private DescriptorValueHolder valueHolder;

	/**
	 * Generic descriptor constructor
	 */
	public PropertyDescriptor(Scriptable thisObject) {
		this.thisObject = thisObject;
		valueHolder = new DataDescriptorValueHolder();
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return valueHolder.get();
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		valueHolder.set(value);
	}

	/**
	 * @return the writeable
	 */
	public boolean isWriteable() {
		return valueHolder.isWriteable();
	}

	/**
	 * @param writeable
	 *            the writeable to set
	 */
	public void setWriteable(boolean writeable) {
		valueHolder.setWriteable(writeable);
	}

	/**
	 * @return the enumerable
	 */
	public boolean isEnumerable() {
		return enumerable;
	}

	/**
	 * @param enumerable
	 *            the enumerable to set
	 */
	public void setEnumerable(boolean enumerable) {
		this.enumerable = enumerable;
	}

	/**
	 * @return the configurable
	 */
	public boolean isConfigurable() {
		return configurable;
	}

	public void setConfigurable(boolean b) {
		this.configurable = b;
	}

	public PropertyDescriptor isWriteable(boolean b) {
		setWriteable(b);
		return this;
	}

	public PropertyDescriptor isEnumerable(boolean b) {
		enumerable = b;
		return this;
	}

	public PropertyDescriptor isConfigurable(boolean b) {
		configurable = b;
		return this;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE);// .append("value", value);
		return builder.toString();
	}

	/**
	 * 
	 * @param callable
	 */
	public void setSetter(Callable callable) {
		if (!(valueHolder instanceof AccessorDescriptorValueHolder)) {
			valueHolder = new AccessorDescriptorValueHolder();
		}
		((AccessorDescriptorValueHolder) valueHolder)
				.setSetterCallable(callable);

	}

	/**
	 * 
	 * @param callable
	 */
	public void setGetter(Callable callable) {
		if (!(valueHolder instanceof AccessorDescriptorValueHolder)) {
			valueHolder = new AccessorDescriptorValueHolder();
		}
		((AccessorDescriptorValueHolder) valueHolder)
				.setGetterCallable(callable);
	}

	public PropertyDescriptor cloneDescriptor(Scriptable thisObject) {
		return valueHolder.cloneDescriptor(thisObject);
	}

	private boolean isDataDescriptor() {
		return valueHolder.isDataDescriptor();
	}

	private boolean isAccessorDescriptor() {
		return valueHolder.isAccessorDescriptor();
	}

	public static boolean isDataDescriptor(PropertyDescriptor descriptor) {
		return descriptor != null && descriptor.isDataDescriptor();
	}

	public static boolean isAccessorDescriptor(PropertyDescriptor descriptor) {
		return descriptor != null && descriptor.isAccessorDescriptor();
	}

	public static boolean isGenericDescriptor(PropertyDescriptor desc) {
		return desc != null && !isDataDescriptor(desc)
				&& !isAccessorDescriptor(desc);
	}

	private static interface DescriptorValueHolder {

		void set(Object value);

		boolean isAccessorDescriptor();

		boolean isDataDescriptor();

		Object get();

		void setWriteable(boolean writeable);

		boolean isWriteable();

		PropertyDescriptor cloneDescriptor(Scriptable thisObject);

	}

	private final class DataDescriptorValueHolder implements
			DescriptorValueHolder {

		private Object value = Undefined.Value;
		private boolean valueSet;
		private boolean writeable;

		@Override
		public void set(Object value) {
			this.value = value;
			this.valueSet = true;
		}

		@Override
		public Object get() {
			return value;
		}

		@Override
		public void setWriteable(boolean writeable) {
			this.writeable = writeable;

		}

		@Override
		public boolean isWriteable() {
			return writeable;
		}

		@Override
		public boolean isAccessorDescriptor() {
			return false;
		}

		@Override
		public boolean isDataDescriptor() {
			return isWriteable() && valueSet;
		}

		@Override
		public PropertyDescriptor cloneDescriptor(Scriptable thisObj) {
			PropertyDescriptor newDescriptor = new PropertyDescriptor(thisObj)
					.isConfigurable(PropertyDescriptor.this.isConfigurable())
					.isEnumerable(PropertyDescriptor.this.isEnumerable())
					.isWriteable(PropertyDescriptor.this.isWriteable());
			newDescriptor.setValue(PropertyDescriptor.this.getValue());
			return newDescriptor;
		}
	}

	private final class AccessorDescriptorValueHolder implements
			DescriptorValueHolder {

		private Callable getterCallable;
		private Callable setterCallable;

		private AccessorDescriptorValueHolder() {
			super();
		}

		@Override
		public Object get() {
			return getterCallable.call(new ScriptableObject(),
					PropertyDescriptor.this.thisObject, EMPTY_ARGS);
		}

		@Override
		public void set(Object value) {
			setterCallable.call(new ScriptableObject(),
					PropertyDescriptor.this.thisObject, new Object[] { value });
		}

		@Override
		public void setWriteable(boolean writeable) {
			// does nothing as is write or throw exception as this method should
			// not be invoked on accessor property
		}

		@Override
		public boolean isWriteable() {
			return setterCallable != null && getterCallable != null;
		}

		/**
		 * @param getterCallable
		 *            the getterCallable to set
		 */
		public void setGetterCallable(Callable getterCallable) {
			this.getterCallable = getterCallable;
		}

		/**
		 * @param setterCallable
		 *            the setterCallable to set
		 */
		public void setSetterCallable(Callable setterCallable) {
			this.setterCallable = setterCallable;
		}

		@Override
		public boolean isAccessorDescriptor() {
			return isWriteable();
		}

		@Override
		public boolean isDataDescriptor() {
			return false;
		}

		@Override
		public PropertyDescriptor cloneDescriptor(Scriptable thisObj) {
			PropertyDescriptor newDesc = new PropertyDescriptor(thisObj)
					.isConfigurable(PropertyDescriptor.this.isConfigurable())
					.isEnumerable(PropertyDescriptor.this.isEnumerable());
			newDesc.setGetter(getterCallable);
			newDesc.setSetter(setterCallable);
			return newDesc;
		}
	}

}
