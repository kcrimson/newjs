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

	public Reference(Object base, String referencedName) {
		super();
		this.base = base;
		this.referencedName = referencedName;
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
		// for speed improvements using direct class compare
		if (object != null && Reference.class.equals(object.getClass())) {
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
			return ((EnvironmentRecords) base).getBindingValue(ref
					.getReferencedName());
		}
		return object;
	}

	private static Object getPrimitiveValue(Object base) {
		Object obj = Convertions.toObject(base);
		return null;
	}

	public void setBase(Object value) {
		base = value;
	}

	public static void putValue(Object object, Object value) {
		if (!(object instanceof Reference)) {
			throw new ReferenceErrorException();
		}

		Reference reference = (Reference) object;
		Object base = reference.getBase();

		((EnvironmentRecords) base).setMutableBinding(
				reference.getReferencedName(), value);
	}
}
