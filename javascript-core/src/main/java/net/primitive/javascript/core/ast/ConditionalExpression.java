package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class ConditionalExpression extends Expression {

	private final Expression op1;
	private final Expression op2;
	private final Expression op3;

	public ConditionalExpression(Expression op1, Expression op2, Expression op3) {
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitConditionalExpression(this);
	}

}
