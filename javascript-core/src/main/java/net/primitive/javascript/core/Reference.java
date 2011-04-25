package net.primitive.javascript.core;

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

	public abstract boolean isPropertyReference();

	public abstract Object getValue();

	public abstract void setValue(Object value);

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

}
