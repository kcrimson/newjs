package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Statement;

public class ExecutionContext {

	private LexicalEnvironment lexicalEnvironment;

	private final LexicalEnvironment variableEnvironment;

	private final Scriptable thisBinding;

	private final Statement statement;

	protected ExecutionContext(LexicalEnvironment lexicalEnvironment,
			LexicalEnvironment variableEnvironment, Scriptable thisBinding,
			Statement statement) {
		super();
		this.lexicalEnvironment = lexicalEnvironment;
		this.variableEnvironment = variableEnvironment;
		this.thisBinding = thisBinding;
		this.statement = statement;
	}

	/**
	 * @return the lexicalEnvironment
	 */
	public LexicalEnvironment getLexicalEnvironment() {
		return lexicalEnvironment;
	}

	/**
	 * @param lexicalEnvironment
	 *            the lexicalEnvironment to set
	 */
	public void setLexicalEnvironment(LexicalEnvironment lexicalEnvironment) {
		this.lexicalEnvironment = lexicalEnvironment;
	}

	/**
	 * @return the variableEnvironment
	 */
	public LexicalEnvironment getVariableEnvironment() {
		return variableEnvironment;
	}

	/**
	 * @return the thisBinding
	 */
	public Scriptable getThisBinding() {
		return thisBinding;
	}

	public void accept(StatementVisitorImpl visitor) {
		statement.accept(visitor);
	}

}
