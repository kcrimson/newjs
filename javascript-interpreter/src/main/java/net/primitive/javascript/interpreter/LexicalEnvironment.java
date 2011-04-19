package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;

public class LexicalEnvironment {

	private final LexicalEnvironment outerLexicalEnvironment;

	private final EnvironmentRecords environmentRecords;

	private LexicalEnvironment(LexicalEnvironment outerLexicalEnvironment,
			EnvironmentRecords environmentRecords) {
		super();
		this.outerLexicalEnvironment = outerLexicalEnvironment;
		this.environmentRecords = environmentRecords;
	}

	/**
	 * @return the outerLexicalEnvironment
	 */
	public LexicalEnvironment getOuterLexicalEnvironment() {
		return outerLexicalEnvironment;
	}

	/**
	 * @return the environmentRecords
	 */
	public EnvironmentRecords getEnvironmentRecords() {
		return environmentRecords;
	}

	public static LexicalEnvironment newObjectEnvironment(
			Scriptable bindingObject, LexicalEnvironment outerLexicalEnvironment) {
		return new LexicalEnvironment(outerLexicalEnvironment,
				new ObjectEnvironmentRecords(bindingObject, true));
	}

	public static LexicalEnvironment newDeclarativeEnvironment(
			LexicalEnvironment outerLexicalEnvironment) {
		return new LexicalEnvironment(outerLexicalEnvironment,
				new DeclarativeEnvironmentRecords());
	}

	public static Reference getIdentifierReference(LexicalEnvironment env,
			String name) {
		if (env == null) {
			return new Reference(Undefined.Value, name);
		}

		EnvironmentRecords records = env.getEnvironmentRecords();
		if (records.hasBinding(name)) {
			// TODO look at possible ways to cache Reference instances,
			// otherwise we create Reference every time we call
			// getIdentifierReference
			return new Reference(records, name);
		}

		LexicalEnvironment outerenv = env.getOuterLexicalEnvironment();
		return getIdentifierReference(outerenv, name);
	}
}
