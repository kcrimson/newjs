package net.primitive.javascript.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.primitive.javascript.core.Undefined;

public class DeclarativeEnvironmentRecords implements EnvironmentRecords {

	private Map<String, Reference> bindings = new HashMap<String, Reference>();

	@Override
	public boolean hasBinding(String name) {
		return bindings.containsKey(name);
	}

	@Override
	public void createMutableBinding(String name, boolean d) {
		Reference ref = new Reference(Undefined.Value, name, true);
		bindings.put(name, ref);
	}

	@Override
	public void setMutableBinding(String name, Object value,
			boolean useStrictMode) {
		Reference ref = bindings.get(name);
		ref.setBase(value);
	}

	@Override
	public Reference getBindingValue(String name, boolean useStrictMode) {
		return bindings.get(name);
	}

	@Override
	public boolean deleteBinding(String name) {
		// TODO Auto-generated method stub
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
