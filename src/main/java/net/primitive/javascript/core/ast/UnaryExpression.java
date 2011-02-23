package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.UnaryOperator;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class UnaryExpression extends Expression {

	private final UnaryOperator operator;
	private final Expression operand;

	public UnaryExpression(UnaryOperator operator, Expression expression) {
		this.operator = operator;
		this.operand = expression;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitUnaryExpression(this);
	}

	/**
	 * @return the operator
	 */
	public UnaryOperator getOperator() {
		return operator;
	}

	/**
	 * @return the expression
	 */
	public Expression getOperand() {
		return operand;
	}

}
