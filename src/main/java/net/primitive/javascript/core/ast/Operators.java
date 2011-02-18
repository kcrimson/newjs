package net.primitive.javascript.core.ast;

import static net.primitive.javascript.core.Convertions.toNumber;
import static net.primitive.javascript.core.Convertions.toPrimitive;
import net.primitive.javascript.core.Convertions;

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
	public static final BinaryOperator GreaterThan = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			return Convertions.toNumber(op1) > Convertions.toNumber(op2);
		}
	};
	public static final BinaryOperator LessThanOrEqual = null;
	public static final BinaryOperator LessThan = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			return Convertions.toNumber(op1) < Convertions.toNumber(op2);
		}
	};
	public static final BinaryOperator InstanceOf = null;
	public static final BinaryOperator GreaterThanOrEual = null;
	public static final BinaryOperator In = null;
	public static final BinaryOperator RightShift = null;
	public static final BinaryOperator UnsignedRightShift = null;
	public static final BinaryOperator LeftShift = null;
	public static final BinaryOperator Plus = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {

			Object primitive1 = toPrimitive(op1);
			Object primitive2 = toPrimitive(op2);

			if (primitive1 instanceof String && primitive2 instanceof String) {
				return (String) primitive1 + (String) primitive2;
			}

			return toNumber(primitive1) + toNumber(primitive2);
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
	public static final AssignmentOperator Assign = null;

	private Operators() {
	}
}
