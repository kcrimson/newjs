package net.primitive.javascript.interpreter;

public interface EnvironmentRecords {

	boolean hasBinding(String name);

	void createMutableBinding(String name, boolean d);

	void setMutableBinding(String name, Object value, boolean useStrictMode);

	Object getBindingValue(String name, boolean useStrictMode);

	boolean deleteBinding(String name);

	Object implicitThisValue();
}
