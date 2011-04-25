package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scriptable;

class ObjectReference extends Reference {

	private final Object base;

	public ObjectReference(Object base, String referencedName) {
		super(referencedName);
		this.base = base;
	}

	@Override
	public Object getBase() {
		return base;
	}

	@Override
	public Object getValue() {
		return getBindingObject().get(getReferencedName());
	}

	@Override
	public void setValue(Object value) {
		getBindingObject().put(getReferencedName(), value);
	}

	private Scriptable getBindingObject() {
		if (base instanceof Scriptable) {
			return (Scriptable) base;
		} else {
			return (Scriptable) Convertions.toObject(base);
		}
	}

	@Override
	public boolean isPropertyReference() {
		return base instanceof Scriptable || hasPrimitiveBase();
	}

	private boolean hasPrimitiveBase() {
		return base instanceof String || base instanceof Number
				|| base instanceof Boolean;
	}

}