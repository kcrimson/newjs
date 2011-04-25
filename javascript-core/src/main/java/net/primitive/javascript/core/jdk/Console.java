package net.primitive.javascript.core.jdk;

import net.primitive.javascript.core.annotations.JSFunction;

public class Console {

	@JSFunction
	public void log(String mesg) {
		System.out.println(mesg);
	}

}
