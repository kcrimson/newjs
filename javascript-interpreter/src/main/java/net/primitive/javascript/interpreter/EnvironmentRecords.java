package net.primitive.javascript.interpreter;

public interface EnvironmentRecords {

	boolean hasBinding(String name);

	Reference createMutableBinding(String name, boolean d);

	Reference getBinding(String name);

	boolean deleteBinding(String name);

	Object implicitThisValue();
}
