package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Undefined;

public class DeclarativeEnvironmentRecords implements EnvironmentRecords {

	@Override
	public boolean hasBinding(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createMutableBinding(String name, boolean d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMutableBinding(String name, Object value,
			boolean useStrictMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getBindingValue(String name, boolean useStrictMode) {
		// TODO Auto-generated method stub
		return null;
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

	public void InitializeImmutableBinding(String name, Object value) {

	}

}
