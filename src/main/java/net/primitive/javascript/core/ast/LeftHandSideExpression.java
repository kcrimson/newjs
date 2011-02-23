package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class LeftHandSideExpression extends Expression {

	private final Expression expression;

	public LeftHandSideExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	/**
	 * @return the exporession
	 */
	public Expression getExpression() {
		return expression;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitLeftHandSideExpression(this);
	}

}
