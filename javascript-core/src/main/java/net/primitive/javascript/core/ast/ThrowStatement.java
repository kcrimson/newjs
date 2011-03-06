package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ThrowStatement extends Statement {

	private final Expression expression;

	public ThrowStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
