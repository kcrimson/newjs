package net.primitive.javascript.core.ast;

public class WhileStatement extends Statement {

	private final Expression expression;
	private final Statement statement;

	public WhileStatement(Expression expression, Statement statement) {
		this.expression = expression;
		this.statement = statement;
	}

}
