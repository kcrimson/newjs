package net.primitive.javascript.core.ast;

public class Operators {
	public static final Operator Equals = null;
	public static final Operator DoesNotEquals = null;
	public static final Operator StrictEquals = null;
	public static final Operator StrictDoesNotEquals = null;
	public static final Operator LogicalAND = null;
	public static final Operator LogicalOR = null;
	public static final Operator BitwiseOR = null;
	public static final Operator BitwiseXOR = null;
	public static final Operator BitwiseAND = null;
	public static final Operator GreaterThan = null;
	public static final Operator LessThanOrEqual = null;
	public static final Operator LessThan = null;
	public static final Operator InstanceOf = null;
	public static final Operator GreaterThanOrEual = null;
	public static final Operator In = null;
	public static final Operator RightShift = null;
	public static final Operator UnsignedRightShift = null;
	public static final Operator LeftShift = null;
	public static final Operator Plus = new Operator() {

		@Override
		public Expression operator(Expression op1, Expression op2) {
			Object object1 = op1.evaluate();
			Object object2 = op2.evaluate();

			if (object1 instanceof Expression && object2 instanceof Expression) {
				return operator((Expression) object1, (Expression) object2);
			} else if (object1 instanceof Expression) {
				return operator((Expression) object1, op2);
			} else if (object2 instanceof Expression) {
				return operator(op1, (Expression) op2);
			}
			return new Literal((String) object1 + "+" + (String) object2);
		}
	};
	public static final Operator Minus = new Operator() {

		@Override
		public Object operator(Expression op1, Expression op2) {
			Object object1 = op1.evaluate();
			Object object2 = op2.evaluate();

			if (object1 instanceof Expression && object2 instanceof Expression) {
				return operator((Expression) object1, (Expression) object2);
			} else if (object1 instanceof Expression) {
				return operator((Expression) object1, op2);
			} else if (object2 instanceof Expression) {
				return operator(op1, (Expression) op2);
			}
			return (String) object1 + "-" + (String) object2;
		}
	};
	public static final Operator Modulo = null;
	public static final Operator Multiply = null;
	public static final Operator Divide = null;

	private Operators() {
	}
}
