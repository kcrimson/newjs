package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.ReferenceErrorException;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;

/**
 * A Reference is a resolved name binding.
 * 
 * 
 */
public class Reference {

	private Object base;
	private final String referencedName;
	private final boolean strictReference;

	public Reference(Object base, String referencedName, boolean strictReference) {
		super();
		this.base = base;
		this.referencedName = referencedName;
		this.strictReference = strictReference;
	}

	/**
	 * @return the base
	 */
	public Object getBase() {
		return base;
	}

	/**
	 * @return the referencedName
	 */
	public String getReferencedName() {
		return referencedName;
	}

	/**
	 * @return the strictReference
	 */
	public boolean isStrictReference() {
		return strictReference;
	}

	public boolean hasPrimitiveBase() {
		return base instanceof String || base instanceof Number
				|| base instanceof Boolean;
	}

	public boolean isPropertyReference() {
		return base instanceof Scriptable || hasPrimitiveBase();
	}

	public boolean isUnresolvableReference() {
		return base == Undefined.Value;
	}

	public static Object getValue(Object object) {
		if (object instanceof Reference) {
			Reference ref = (Reference) object;
			if (ref.isUnresolvableReference()) {
				throw new ReferenceErrorException();
			}

			Object base = ref.getBase();
			if (ref.isPropertyReference()) {
				if (ref.hasPrimitiveBase()) {
					return getPrimitiveValue(base);
				} else {
					return ((Scriptable) base).get(ref.getReferencedName());
				}
			}
			return ((EnvironmentRecords) base).getBindingValue(
					ref.getReferencedName(), ref.isStrictReference());
		}
		return object;
	}

	private static Object getPrimitiveValue(Object base) {
		Object obj = Convertions.toObject(base);
		return null;
	}
}
