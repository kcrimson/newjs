package net.primitive.javascript.core.ast;

public class BinaryExpression extends Expression {

	private final Operator operator;
	private final Expression op1;
	private Expression op2;

	public BinaryExpression(Operator operator, Expression op1, Expression op2) {
		this.operator = operator;
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public Object evaluate() {
		return operator.operator(op1, op2);
	}

}
