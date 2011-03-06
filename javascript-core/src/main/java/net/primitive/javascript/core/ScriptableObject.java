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
	public Object get(String name, Scriptable start) {

		Object value = NotFound;

		if (start != null) {
			value = start.get(name, null);
		}

		if (value == NotFound) {

			value = associatedValues.get(name).getValue();

			if (value == NotFound && prototype != null) {
				value = get(name, prototype);
			}
		}

		if (value == NotFound && parentScope != null) {
			// looking up parent scope
			value = get(name, parentScope);
		}

		return value;
	}

	@Override
	public Object get(int index, Scriptable start) {
		return associatedValues.get(index);
	}

	@Override
	public boolean has(String name, Scriptable start) {
		return associatedValues.containsKey(name);
	}

	@Override
	public boolean has(int index, Scriptable start) {
		return associatedValues.containsKey(index);
	}

	@Override
	public void put(String name, Scriptable start, Object value) {
		associatedValues.put(name, new ScriptableObjectProperty(name, value));
	}

	@Override
	public void put(int index, Scriptable start, Object value) {
		// associatedValues.put(index, value);
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

	@Override
	public ScriptableObjectProperty getProperty(String identfierName) {
		return associatedValues.get(identfierName);
	}

}
