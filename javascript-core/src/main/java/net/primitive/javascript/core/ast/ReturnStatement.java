package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class ReturnStatement extends Statement {

	private final Expression expression;

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitReturnStatement(this);
	}

}
