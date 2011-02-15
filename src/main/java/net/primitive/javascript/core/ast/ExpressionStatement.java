package net.primitive.javascript.core.ast;

public class ExpressionStatement extends Statement {

	private final Expression expression;

	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}
}
