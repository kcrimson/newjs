package net.primitive.javascript.core;

public class ScriptableObjectProperty {

	private final String name;

	private Object value;

	private boolean writeable;

	private boolean enumerable;

	private final boolean configurable;

	public ScriptableObjectProperty(String name) {
		super();
		this.name = name;
		this.value = Undefined.Value;
		this.configurable = true;
	}

	public ScriptableObjectProperty(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
		this.configurable = true;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the writeable
	 */
	public boolean isWriteable() {
		return writeable;
	}

	/**
	 * @param writeable
	 *            the writeable to set
	 */
	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
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

}
