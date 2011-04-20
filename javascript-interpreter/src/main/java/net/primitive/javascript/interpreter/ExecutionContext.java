package net.primitive.javascript.interpreter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.Statement;

public class ExecutionContext {

	private LexicalEnvironment lexicalEnvironment;

	private final LexicalEnvironment variableEnvironment;

	private final Scriptable thisBinding;

	private final Statement statement;

	private Completion completion;

	protected ExecutionContext(LexicalEnvironment lexicalEnvironment,
			LexicalEnvironment variableEnvironment, Scriptable thisBinding,
			Statement statement) {
		super();
		this.lexicalEnvironment = lexicalEnvironment;
		this.variableEnvironment = variableEnvironment;
		this.thisBinding = thisBinding;
		this.statement = statement;
		completion = new Completion(CompletionType.Normal, Undefined.Value,
				null);
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

	public Completion getCompletion() {
		return completion;
	}

	public void returnValue(Object value) {
		completion = new Completion(CompletionType.Return, value, null);
	}

	public void throwException(Object exceptionObject) {
		completion = new Completion(CompletionType.Throw, exceptionObject, null);
	}

	public Statement getStatement() {
		return statement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder stringBuilder = new ToStringBuilder(this,
				ToStringStyle.SIMPLE_STYLE).append("statement", statement);
		return stringBuilder.toString();
	}

	public void normalCompletion() {
		completion = new Completion(CompletionType.Normal, null, null);

	}

}
