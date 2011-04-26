package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;

public final class LexicalEnvironment {

	private LexicalEnvironment() {
	}

	public static Scope newObjectEnvironment(Scriptable bindingObject,
			Scope outerScope) {
		return new Scope(outerScope, new ObjectEnvironmentRecords(
				bindingObject, true));
	}

	public static Scope newDeclarativeEnvironment(Scope outerScope) {
		return new Scope(outerScope, new DeclarativeEnvironmentRecords());
	}

	public static Reference getIdentifierReference(Scope env, String name) {
		if (env == null) {
			return new ObjectReference(Undefined.Value, name);
		}

		ScopeBindings records = env.getBindings();
		if (records.hasBinding(name)) {
			// TODO look at possible ways to cache Reference instances,
			// otherwise we create Reference every time we call
			// getIdentifierReference
			return records.getBinding(name);
		}

		Scope outerenv = env.getOuterScope();
		return getIdentifierReference(outerenv, name);
	}
}
