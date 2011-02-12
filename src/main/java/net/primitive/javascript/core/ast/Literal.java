package net.primitive.javascript.core.ast;

public class Literal extends Expression {

	private final Object value;

	public Literal(Object value) {
		this.value = value;
	}

	@Override
	public Object evaluate() {
		return value;
	}

}
