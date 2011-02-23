package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ForStatement extends Statement {

	private final Statement statement;
	private final Expression expression;
	private final Expression expression2;
	private final Statement statement2;

	public ForStatement(Statement statement, Expression expression,
			Expression expression2, Statement statement2) {
		this.statement = statement;
		this.expression = expression;
		this.expression2 = expression2;
		this.statement2 = statement2;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForStatement(this);
	}

}
