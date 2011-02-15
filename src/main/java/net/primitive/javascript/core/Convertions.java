package net.primitive.javascript.core;

public class Convertions {

	public boolean toBoolean(Object object) {
		if (object == null) {
			return false;
		} else if (Undefined.Value.equals(object)) {
			return false;
		}
		return true;
	}
}
