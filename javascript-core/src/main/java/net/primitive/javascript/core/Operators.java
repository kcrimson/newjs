/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.core;

import static net.primitive.javascript.core.Convertions.toBoolean;
import static net.primitive.javascript.core.Convertions.toNumber;
import static net.primitive.javascript.core.Convertions.toObject;
import static net.primitive.javascript.core.Convertions.toPrimitive;
import static net.primitive.javascript.core.Reference.getValue;
import static net.primitive.javascript.core.Reference.putValue;

import net.primitive.javascript.core.ast.AssignmentOperator;

/**
 * Set of static code which implements all operators available in ECMAScript.
 * @author jpalka@gmail.com
 *
 */
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
	public static final BinaryOperator InstanceOf = new BinaryOperator() {

		@Override
		public Object operator(Object lvar, Object rvar) {
			if (rvar instanceof Function) {
				return ((Function) rvar).hasInstance(lvar);
			}
			throw new TypeErrorException();
		}
	};

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
			double number1 = Convertions.toNumber(op1);
			double number2 = Convertions.toNumber(op2);
			return number1 - number2;
		}
	};
	public static final BinaryOperator Modulo = null;
	public static final BinaryOperator Multiply = new BinaryOperator() {

		@Override
		public Object operator(Object op1, Object op2) {
			double number1 = Convertions.toNumber(op1);
			double number2 = Convertions.toNumber(op2);
			return number1 * number2;
		}
	};
	public static final BinaryOperator Divide = null;

	public static final UnaryOperator Delete = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			if (object instanceof Reference) {
				Reference ref = (Reference) object;
				if (ref.isUnresolvableReference()) {
					// TODO throw SyntaxError
					return false;
				} else if (ref.isPropertyReference()) {
					Object base = ref.getBase();
					Scriptable object2 = toObject(base);
					return object2.delete(ref.getReferencedName(), false);
				}
			}
			return true;
		}
	};

	public static final AssignmentOperator Assign = null;

	public static final UnaryOperator TypeOf = new UnaryOperator() {

		@Override
		public Object operator(Object object) {

			Object value = getValue(object);

			if (Undefined.Value == value) {
				return Types.Undefined;
			}
			if (value == null) {
				return Types.Object;
			}
			if (value instanceof Boolean) {
				return Types.Boolean;
			}
			if (value instanceof Number) {
				return Types.Number;
			}
			if (value instanceof String) {
				return Types.String;
			}
			if ((value instanceof Scriptable) && !(value instanceof Function)) {
				return Types.Object;
			}
			if (value instanceof Function) {
				return Types.Function;
			}
			return "object";
		}
	};

	public static final UnaryOperator Not = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			return !toBoolean(getValue(object));
		}
	};

	public static final UnaryOperator PostfixIncrement = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			double oldValue = toNumber(getValue(object));
			putValue(object, oldValue + 1);
			return oldValue;
		}
	};

	public static final UnaryOperator PostfixDecrement = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			double oldValue = toNumber(getValue(object));
			putValue(object, oldValue - 1);
			return oldValue;
		}
	};

	public static final UnaryOperator PrefixDecrement = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			double oldValue = toNumber(getValue(object));
			double newValue = oldValue - 1;
			putValue(object, newValue);
			return newValue;
		}
	};

	public static final UnaryOperator PrefixIncrement = new UnaryOperator() {

		@Override
		public Object operator(Object object) {
			double oldValue = toNumber(getValue(object));
			double newValue = oldValue + 1;
			putValue(object, newValue);
			return newValue;
		}
	};

	private Operators() {
	}
}
