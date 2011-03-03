package net.primitive.javascript.core.ast;

public class NameValuePair {

	private final Object name;

	private final Expression value;

	public NameValuePair(Object name, Expression value) {
		super();
		this.name = name;
		this.value = value;
		
		System.out.println(name);
	}

	/**
	 * @return the name
	 */
	public Object getName() {
		return name;
	}

	/**
	 * @return the value
	 */
	public Expression getValue() {
		return value;
	}

}
