package net.primitive.javascript.core;

import net.primitive.javascript.core.natives.JSBoolean;
import net.primitive.javascript.core.natives.JSNumber;
import net.primitive.javascript.core.natives.JSString;

public class Convertions {

	private Convertions() {
	}

	public static Object toObject(Object object) {
		if (object == null || object == Undefined.Value) {
			throw new TypeErrorException();
		}
		if (object instanceof Boolean) {
			return new JSBoolean((Boolean) object);
		}
		if (object instanceof Number) {
			return new JSNumber((Number) object);
		}
		if (object instanceof String) {
			return new JSString((String) object);
		}
		return object;
	}

	// TODO ale gowno, co zrobic kiedy mamy javowy typ
	public static Object toPrimitive(Object object) {
		if (object == null || Undefined.Value == object
				|| object instanceof String || object instanceof Boolean
				|| object instanceof Number) {
			return object;
		}
		if (object instanceof Scriptable) {
			return ((Scriptable) object).getDefaultValue(null);
		}
		return Undefined.Value;
	}

	public static boolean toBoolean(Object object) {
		if (object == null) {
			return false;
		}
		if (Undefined.Value.equals(object)) {
			return false;
		}
		if (object instanceof String) {
			return ((String) object).isEmpty();
		}
		if (object instanceof Number) {
			return ((Number) object).doubleValue() != 0.0;
		}
		if (object instanceof Boolean) {
			return ((Boolean) object).booleanValue();
		}
		if (object instanceof Scriptable) {
			Object val = ((Scriptable) object).getDefaultValue(Boolean.class);
			if (val instanceof Scriptable) {
				// val error, bo mial bys primitive
			}
			return toBoolean(val);
		}
		return true;
	}

	public static double toNumber(Object val) {
		if (val instanceof Number)
			return ((Number) val).doubleValue();
		if (val == null)
			return +0.0;
		if (val == Undefined.Value)
			return Double.NaN;
		if (val instanceof String)
			return toNumber((String) val);
		if (val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1 : +0.0;
		if (val instanceof Scriptable) {
			val = ((Scriptable) val).getDefaultValue(Number.class);
			if (val instanceof Scriptable) {
				// val errorthrow errorWithClassName("msg.primitive.expected",
				// val);
			}
			return toNumber(val);
		}
		return Double.NaN;
	}

	/**
	 * ToNumber applied to the String type
	 * 
	 * See ECMA 9.3.1
	 */
	public static double toNumber(String s) {
		int len = s.length();
		int start = 0;
		char startChar;
		for (;;) {
			if (start == len) {
				// Empty or contains only whitespace
				return +0.0;
			}
			startChar = s.charAt(start);
			if (!Character.isWhitespace(startChar))
				break;
			start++;
		}

		if (startChar == '0') {
			if (start + 2 < len) {
				int c1 = s.charAt(start + 1);
				if (c1 == 'x' || c1 == 'X') {
					// A hexadecimal number
					return stringToNumber(s, start + 2, 16);
				}
			}
		} else if (startChar == '+' || startChar == '-') {
			if (start + 3 < len && s.charAt(start + 1) == '0') {
				int c2 = s.charAt(start + 2);
				if (c2 == 'x' || c2 == 'X') {
					// A hexadecimal number with sign
					double val = stringToNumber(s, start + 3, 16);
					return startChar == '-' ? -val : val;
				}
			}
		}

		int end = len - 1;
		char endChar;
		while (Character.isWhitespace(endChar = s.charAt(end)))
			end--;
		if (endChar == 'y') {
			// check for "Infinity"
			if (startChar == '+' || startChar == '-')
				start++;
			if (start + 7 == end && s.regionMatches(start, "Infinity", 0, 8))
				return startChar == '-' ? Double.NEGATIVE_INFINITY
						: Double.POSITIVE_INFINITY;
			return Double.NaN;
		}
		// A non-hexadecimal, non-infinity number:
		// just try a normal floating point conversion
		String sub = s.substring(start, end + 1);

		try {
			return Double.valueOf(sub).doubleValue();
		} catch (NumberFormatException ex) {
			return Double.NaN;
		}
	}

	/*
	 * Helper function for toNumber, parseInt, and TokenStream.getToken.
	 */
	static double stringToNumber(String s, int start, int radix) {
		char digitMax = '9';
		char lowerCaseBound = 'a';
		char upperCaseBound = 'A';
		int len = s.length();
		if (radix < 10) {
			digitMax = (char) ('0' + radix - 1);
		}
		if (radix > 10) {
			lowerCaseBound = (char) ('a' + radix - 10);
			upperCaseBound = (char) ('A' + radix - 10);
		}
		int end;
		double sum = 0.0;
		for (end = start; end < len; end++) {
			char c = s.charAt(end);
			int newDigit;
			if ('0' <= c && c <= digitMax)
				newDigit = c - '0';
			else if ('a' <= c && c < lowerCaseBound)
				newDigit = c - 'a' + 10;
			else if ('A' <= c && c < upperCaseBound)
				newDigit = c - 'A' + 10;
			else
				break;
			sum = sum * radix + newDigit;
		}
		if (start == end) {
			return Double.NaN;
		}
		if (sum >= 9007199254740992.0) {
			if (radix == 10) {
				/*
				 * If we're accumulating a decimal number and the number is >=
				 * 2^53, then the result from the repeated multiply-add above
				 * may be inaccurate. Call Java to get the correct answer.
				 */
				try {
					return Double.valueOf(s.substring(start, end))
							.doubleValue();
				} catch (NumberFormatException nfe) {
					return Double.NaN;
				}
			} else if (radix == 2 || radix == 4 || radix == 8 || radix == 16
					|| radix == 32) {
				/*
				 * The number may also be inaccurate for one of these bases.
				 * This happens if the addition in value*radix + digit causes a
				 * round-down to an even least significant mantissa bit when the
				 * first dropped bit is a one. If any of the following digits in
				 * the number (which haven't been added in yet) are nonzero then
				 * the correct action would have been to round up instead of
				 * down. An example of this occurs when reading the number
				 * 0x1000000000000081, which rounds to 0x1000000000000000
				 * instead of 0x1000000000000100.
				 */
				int bitShiftInChar = 1;
				int digit = 0;

				final int SKIP_LEADING_ZEROS = 0;
				final int FIRST_EXACT_53_BITS = 1;
				final int AFTER_BIT_53 = 2;
				final int ZEROS_AFTER_54 = 3;
				final int MIXED_AFTER_54 = 4;

				int state = SKIP_LEADING_ZEROS;
				int exactBitsLimit = 53;
				double factor = 0.0;
				boolean bit53 = false;
				// bit54 is the 54th bit (the first dropped from the mantissa)
				boolean bit54 = false;

				for (;;) {
					if (bitShiftInChar == 1) {
						if (start == end)
							break;
						digit = s.charAt(start++);
						if ('0' <= digit && digit <= '9')
							digit -= '0';
						else if ('a' <= digit && digit <= 'z')
							digit -= 'a' - 10;
						else
							digit -= 'A' - 10;
						bitShiftInChar = radix;
					}
					bitShiftInChar >>= 1;
					boolean bit = (digit & bitShiftInChar) != 0;

					switch (state) {
					case SKIP_LEADING_ZEROS:
						if (bit) {
							--exactBitsLimit;
							sum = 1.0;
							state = FIRST_EXACT_53_BITS;
						}
						break;
					case FIRST_EXACT_53_BITS:
						sum *= 2.0;
						if (bit)
							sum += 1.0;
						--exactBitsLimit;
						if (exactBitsLimit == 0) {
							bit53 = bit;
							state = AFTER_BIT_53;
						}
						break;
					case AFTER_BIT_53:
						bit54 = bit;
						factor = 2.0;
						state = ZEROS_AFTER_54;
						break;
					case ZEROS_AFTER_54:
						if (bit) {
							state = MIXED_AFTER_54;
						}
						// fallthrough
					case MIXED_AFTER_54:
						factor *= 2;
						break;
					}
				}
				switch (state) {
				case SKIP_LEADING_ZEROS:
					sum = 0.0;
					break;
				case FIRST_EXACT_53_BITS:
				case AFTER_BIT_53:
					// do nothing
					break;
				case ZEROS_AFTER_54:
					// x1.1 -> x1 + 1 (round up)
					// x0.1 -> x0 (round down)
					if (bit54 & bit53)
						sum += 1.0;
					sum *= factor;
					break;
				case MIXED_AFTER_54:
					// x.100...1.. -> x + 1 (round up)
					// x.0anything -> x (round down)
					if (bit54)
						sum += 1.0;
					sum *= factor;
					break;
				}
			}
			/* We don't worry about inaccurate numbers for any other base. */
		}
		return sum;
	}

	public static String toString(Object val) {
		return val.toString();
	}

}
