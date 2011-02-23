package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class WithStatement extends Statement {

	private final Expression expression;
	private final Statement statement;

	public WithStatement(Expression expression, Statement statement) {
		this.expression = expression;
		this.statement = statement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
