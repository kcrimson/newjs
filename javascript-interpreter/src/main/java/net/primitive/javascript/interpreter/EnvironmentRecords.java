package net.primitive.javascript.interpreter;

public interface EnvironmentRecords {

	boolean hasBinding(String name);

	void createMutableBinding(String name, boolean d);

	void setMutableBinding(String name, Object value);

	Object getBindingValue(String name);

	boolean deleteBinding(String name);

	Object implicitThisValue();
}
