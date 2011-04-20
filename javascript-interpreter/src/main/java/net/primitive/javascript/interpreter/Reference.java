package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.ReferenceErrorException;
import net.primitive.javascript.core.Undefined;

/**
 * A Reference is a resolved name binding.
 * 
 * 
 */
public abstract class Reference {

	private final String referencedName;

	public Reference(String referencedName) {
		super();
		this.referencedName = referencedName;
	}

	/**
	 * @return the referencedName
	 */
	public String getReferencedName() {
		return referencedName;
	}

	public boolean isUnresolvableReference() {
		return getBase() == Undefined.Value;
	}

	/**
	 * @return the base
	 */
	public abstract Object getBase();

	protected abstract Object getValue();

	protected abstract void setValue(Object value);

	public static Object getValue(Object object) {
		if (object instanceof Reference) {
			Reference ref = (Reference) object;
			if (ref.isUnresolvableReference()) {
				throw new ReferenceErrorException();
			}

			return ref.getValue();
		}
		return object;
	}

	public static void putValue(Object object, Object value) {
		if (!(object instanceof Reference)) {
			throw new ReferenceErrorException();
		}

		Reference reference = (Reference) object;
		reference.setValue(value);
	}

	public abstract boolean isPropertyReference();
}
