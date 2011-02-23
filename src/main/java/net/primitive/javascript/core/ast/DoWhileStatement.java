package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class DoWhileStatement extends Statement {

	private final Statement statement;
	private final Expression expression;

	public DoWhileStatement(Statement statement, Expression expression) {
		this.statement = statement;
		this.expression = expression;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
}
