package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class WrappedExpression extends Expression {

	private final Expression expression1;
	private final Expression expression2;

	public WrappedExpression(Expression exp1, Expression exp2) {
		this.expression1 = exp1;
		this.expression2 = exp2;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitWrappedExpression(this);
	}

	/**
	 * @return the expression1
	 */
	public Expression getExpression1() {
		return expression1;
	}

	/**
	 * @return the expression2
	 */
	public Expression getExpression2() {
		return expression2;
	}

}
