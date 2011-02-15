package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class BinaryExpression extends Expression {

	private final BinaryOperator operator;
	private final Expression op1;
	private final Expression op2;

	public BinaryExpression(BinaryOperator operator, Expression op1,
			Expression op2) {
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
	}

	public BinaryOperator getOperator() {
		return operator;
	}

	public Expression getOp1() {
		return op1;
	}

	public Expression getOp2() {
		return op2;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitBinaryExpression(this);
	}
}
