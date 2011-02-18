package net.primitive.javascript.core;

public class Property {

	private final String name;

	private Object value;

	public Property(String name) {
		super();
		this.name = name;
	}

	public Property(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
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

}
