package net.primitive.javascript.core;

import java.util.HashMap;

public class ScriptableObject implements Scriptable {

	private static final Object NotFound = new Object();

	private HashMap<Object, ScriptableObjectProperty> associatedValues = new HashMap<Object, ScriptableObjectProperty>();

	private Scriptable prototype;

	private Scriptable parentScope;

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScriptableObjectProperty get(String name) {
		return get(name, null);
	}

	public ScriptableObjectProperty get(String name, Scriptable start) {

		Object value = NotFound;

		if (start != null) {
			value = start.get(name);
		}

		if (value == NotFound) {

			value = associatedValues.get(name);

			if (value == null) {
				value = NotFound;
			}
		}

		if (value == NotFound && prototype != null) {
			// lookup property scope
			value = get(name, prototype);
		}

		if (value == NotFound && parentScope != null) {
			// looking up parent scope
			value = get(name, parentScope);
		}

		return (ScriptableObjectProperty) value;
	}

	@Override
	public ScriptableObjectProperty get(int index) {
		return associatedValues.get(index);
	}

	@Override
	public boolean has(String name) {
		return associatedValues.containsKey(name);
	}

	@Override
	public boolean has(int index) {
		return associatedValues.containsKey(index);
	}

	@Override
	public ScriptableObjectProperty put(String name, Object value) {
		ScriptableObjectProperty objectProperty = new ScriptableObjectProperty(
				this, name, value);
		associatedValues.put(name, objectProperty);
		return objectProperty;
	}

	@Override
	public ScriptableObjectProperty put(int index, Object value) {
		// associatedValues.put(index, value);
		return null;
	}

	@Override
	public void delete(String name) {
		associatedValues.remove(name);
	}

	@Override
	public void delete(int index) {
		associatedValues.remove(index);
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
	public Scriptable getParentScope() {
		return parentScope;
	}

	@Override
	public void setParentScope(Scriptable parent) {
		parentScope = parent;
	}

	@Override
	public Object[] getIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDefaultValue(Class<?> hint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasInstance(Scriptable instance) {
		// TODO Auto-generated method stub
		return false;
	}

}
