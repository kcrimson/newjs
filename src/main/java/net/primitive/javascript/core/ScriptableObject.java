package net.primitive.javascript.core;

import java.util.HashMap;

public class ScriptableObject implements Scriptable {

	private HashMap<Object, Object> associatedValues = new HashMap<Object, Object>();

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String name, Scriptable start) {
		return associatedValues.get(name);
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
		associatedValues.put(name, value);
	}

	@Override
	public void put(int index, Scriptable start, Object value) {
		associatedValues.put(index, value);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrototype(Scriptable prototype) {
		// TODO Auto-generated method stub

	}

	@Override
	public Scriptable getParentScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParentScope(Scriptable parent) {
		// TODO Auto-generated method stub

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
