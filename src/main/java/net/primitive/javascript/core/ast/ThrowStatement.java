package net.primitive.javascript.core.ast;

public class ThrowStatement extends Statement {

	private final Expression expression;

	public ThrowStatement(Expression expression) {
		this.expression = expression;
	}

}
