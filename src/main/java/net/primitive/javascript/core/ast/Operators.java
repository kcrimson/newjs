package net.primitive.javascript.core.ast;

public class Operators {
	public static final BinaryOperator Equals = null;
	public static final BinaryOperator DoesNotEquals = null;
	public static final BinaryOperator StrictEquals = null;
	public static final BinaryOperator StrictDoesNotEquals = null;
	public static final BinaryOperator LogicalAND = null;
	public static final BinaryOperator LogicalOR = null;
	public static final BinaryOperator BitwiseOR = null;
	public static final BinaryOperator BitwiseXOR = null;
	public static final BinaryOperator BitwiseAND = null;
	public static final BinaryOperator GreaterThan = null;
	public static final BinaryOperator LessThanOrEqual = null;
	public static final BinaryOperator LessThan = null;
	public static final BinaryOperator InstanceOf = null;
	public static final BinaryOperator GreaterThanOrEual = null;
	public static final BinaryOperator In = null;
	public static final BinaryOperator RightShift = null;
	public static final BinaryOperator UnsignedRightShift = null;
	public static final BinaryOperator LeftShift = null;
	public static final BinaryOperator Plus = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {

			return Integer.toString(Integer.parseInt((String) op1)
					+ +Integer.parseInt((String) op2));
		}
	};

	public static final BinaryOperator Minus = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			int i = Integer.parseInt((String) op1)
					- +Integer.parseInt((String) op2);
			return Integer.toString(i);
		}
	};
	public static final BinaryOperator Modulo = null;
	public static final BinaryOperator Multiply = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			return Integer.toString(Integer.parseInt((String) op1)
					* +Integer.parseInt((String) op2));
		}
	};
	public static final BinaryOperator Divide = null;
	public static final UnaryOperator Delete = null;

	private Operators() {
	}
}
