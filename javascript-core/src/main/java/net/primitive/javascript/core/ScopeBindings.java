package net.primitive.javascript.core;


public interface ScopeBindings {

	boolean hasBinding(String name);

	Reference createMutableBinding(String name, boolean d);

	Reference getBinding(String name);

	boolean deleteBinding(String name);

	Object implicitThisValue();
}
