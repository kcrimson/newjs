package net.primitive.javascript.core.ast;

import lombok.Getter;

public class NameValuePair {

	@Getter private final Object name;
	@Getter private final Expression value;

	public NameValuePair(Object name, Expression value) {
		super();
		this.name = name;
		this.value = value;
		
	//	System.out.println(name);
	}

}
