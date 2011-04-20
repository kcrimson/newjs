package net.primitive.javascript.core;

import static net.primitive.javascript.core.Convertions.toNumber;
import static net.primitive.javascript.core.Convertions.toPrimitive;

import net.primitive.javascript.core.ast.AssignmentOperator;

public class Operators {
	public static final BinaryOperator Equals = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			String type1 = (String) TypeOf.operator(op1);
			String type2 = (String) TypeOf.operator(op2);

			if (type1.equals(type2)) {
				if (Types.Undefined.equals(type1)) {
					return true;
				}
				if (Types.Number.equals(type1)) {
					return ((Number) op1).doubleValue() == ((Number) op2)
							.doubleValue();
				}
				if (Types.String.equals(type1)) {
					return ((String) op1).equals(op2);
				}
			}
			return false;
		}
	};
	public static final BinaryOperator DoesNotEquals = null;
	public static final BinaryOperator StrictEquals = null;
	public static final BinaryOperator StrictDoesNotEquals = null;

	public static final BinaryOperator LogicalAND = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			if (!Convertions.toBoolean(op1)) {
				return op1;
			}
			return op2;
		}
	};

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

	public static final UnaryOperator TypeOf = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			if (Undefined.Value == object) {
				return Types.Undefined;
			}
			if (object == null) {
				return Types.Object;
			}
			if (object instanceof Boolean) {
				return Types.Boolean;
			}
			if (object instanceof Number) {
				return Types.Number;
			}
			if (object instanceof String) {
				return Types.String;
			}
			if ((object instanceof Scriptable) && !(object instanceof Function)) {
				return Types.Object;
			}
			if (object instanceof Function) {
				return Types.Function;
			}
			return "object";
		}
	};

	private Operators() {
	}
}
