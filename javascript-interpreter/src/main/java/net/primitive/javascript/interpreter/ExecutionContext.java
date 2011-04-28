/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.Statement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExecutionContext {

	private Scope lexicalEnvironment;

	private final Scope variableEnvironment;

	private final Scriptable thisBinding;

	private final Statement statement;

	private Completion completion;

	protected ExecutionContext(Scope lexicalEnvironment,
			Scope variableEnvironment, Scriptable thisBinding,
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
	public Scope getLexicalEnvironment() {
		return lexicalEnvironment;
	}

	/**
	 * @param lexicalEnvironment
	 *            the lexicalEnvironment to set
	 */
	public void setLexicalEnvironment(Scope lexicalEnvironment) {
		this.lexicalEnvironment = lexicalEnvironment;
	}

	/**
	 * @return the variableEnvironment
	 */
	public Scope getVariableEnvironment() {
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
				ToStringStyle.SIMPLE_STYLE).append("statement", statement)
				.append("completion", completion);
		return stringBuilder.toString();
	}

	public void normalCompletion() {
		completion = new Completion(CompletionType.Normal, null, null);

	}

	public void breakStatement(String identifier) {
		completion = new Completion(CompletionType.Break, null, null);
	}

}
