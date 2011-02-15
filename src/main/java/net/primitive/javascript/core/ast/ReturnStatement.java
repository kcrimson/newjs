package net.primitive.javascript.core.ast;

public class ReturnStatement extends Statement {

	private final Expression expression;

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

}
