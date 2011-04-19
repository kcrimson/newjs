package net.primitive.javascript.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.primitive.javascript.core.Undefined;

public class DeclarativeEnvironmentRecords implements EnvironmentRecords {

	private Map<String, Object> bindings = new HashMap<String, Object>();

	@Override
	public boolean hasBinding(String name) {
		return bindings.containsKey(name);
	}

	@Override
	public void createMutableBinding(String name, boolean d) {
		// Reference ref = new Reference(Undefined.Value, name);
		bindings.put(name, Undefined.Value);
	}

	@Override
	public void setMutableBinding(String name, Object value) {
		bindings.put(name, value);
	}

	@Override
	public Object getBindingValue(String name) {
		return bindings.get(name);
	}

	@Override
	public boolean deleteBinding(String name) {
		return false;
	}

	@Override
	public Object implicitThisValue() {
		return Undefined.Value;
	}

	public void createImmutableBinding(String name) {

	}

	public void initializeImmutableBinding(String name, Object value) {

	}

}
