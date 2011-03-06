package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class Literal extends Expression {

	private final Object value;

	public Literal(Object value) {
		this.value = value;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitLiteral(this);
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	public static String unwrapString(String string) {
		return string.substring(1, string.length() - 1);
	}

}
