package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ExpressionStatement extends Statement {

	private final Expression expression;

	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitExpressionStatement(this);
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}
	
	
}
