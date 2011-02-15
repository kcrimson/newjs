package net.primitive.javascript.core.ast;

public class DoWhileStatement extends Statement {

	private final Statement statement;
	private final Expression expression;

	public DoWhileStatement(Statement statement, Expression expression) {
		this.statement = statement;
		this.expression = expression;
	}
}
