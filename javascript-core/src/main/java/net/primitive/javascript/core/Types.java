package net.primitive.javascript.core;

public final class Types {

	public final static String Object = "object";

	public final static String Function = "function";

	public final static String Number = "number";

	public final static String String = "string";

	public final static String Boolean = "boolean";

	public final static String Undefined = "undefined";

	private Types() {

	}

	public static boolean isConstructor(Object obj) {
		return obj instanceof Constructor;
	}
}
