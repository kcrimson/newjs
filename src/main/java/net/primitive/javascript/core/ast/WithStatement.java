package net.primitive.javascript.core.ast;

public class WithStatement extends Statement {

	private final Expression expression;
	private final Statement statement;

	public WithStatement(Expression expression, Statement statement) {
		this.expression = expression;
		this.statement = statement;
	}

}
