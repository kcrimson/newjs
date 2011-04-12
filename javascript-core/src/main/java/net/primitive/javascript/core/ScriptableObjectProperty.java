package net.primitive.javascript.core;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ScriptableObjectProperty {

	private final String name;

	private Object value;

	private boolean writeable;

	private boolean enumerable;

	private final boolean configurable;

	private final Scriptable scope;

	public ScriptableObjectProperty(Scriptable scope, String name) {
		super();
		this.scope = scope;
		this.name = name;
		this.value = Undefined.Value;
		this.configurable = true;
	}

	public ScriptableObjectProperty(Scriptable scope, String name, Object value) {
		super();
		this.name = name;
		this.value = value;
		this.configurable = true;
		this.scope = scope;
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

	/**
	 * @return the scope
	 */
	public Scriptable getScope() {
		return scope;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).append("value", value);
		return builder.toString();
	}
}
