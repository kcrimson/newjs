package net.primitive.javascript.interpreter;

import java.util.HashMap;
import java.util.Map;

import net.primitive.javascript.core.Undefined;

public class DeclarativeEnvironmentRecords implements EnvironmentRecords {

	private Map<String, Reference> bindings = new HashMap<String, Reference>();

	@Override
	public boolean hasBinding(String name) {
		return bindings.containsKey(name);
	}

	@Override
	public Reference createMutableBinding(String name, boolean d) {
		Reference ref = new DeclarativeReference(name);
		bindings.put(name, ref);
		return ref;
	}

	@Override
	public boolean deleteBinding(String name) {
		return false;
	}

	@Override
	public Object implicitThisValue() {
		return Undefined.Value;
	}

	public void createImmutableBinding(String name) {

	}

	public void initializeImmutableBinding(String name, Object value) {

	}

	private class DeclarativeReference extends Reference {

		private Object value = Undefined.Value;

		public DeclarativeReference(String referencedName) {
			super(referencedName);
		}

		@Override
		public Object getBase() {
			return DeclarativeEnvironmentRecords.this;
		}

		@Override
		protected Object getValue() {
			return value;
		}

		@Override
		protected void setValue(Object value) {
			this.value = value;
		}

		@Override
		public boolean isPropertyReference() {
			return false;
		}

	}

	@Override
	public Reference getBinding(String name) {
		return bindings.get(name);
	}

}
