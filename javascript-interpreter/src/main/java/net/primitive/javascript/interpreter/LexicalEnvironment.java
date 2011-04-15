package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;

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
}
