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

import static net.primitive.javascript.interpreter.RuntimeContext.currentContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.TypeErrorException;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.AstNodeList;
import net.primitive.javascript.core.ast.Statement;

public class JSNativeFunction extends ScriptableObject implements Function {

	private final String functionName;
	private final List<String> parameterList;
	private final AstNodeList functionBody;
	private final Scope scope;

	public JSNativeFunction(Scope scope, String functionName,
			List<String> parameterList, AstNodeList functionBody) {
		this.scope = scope;
		this.functionName = functionName;
		this.parameterList = parameterList;
		this.functionBody = functionBody;
	}

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {

		Object returnValue = Undefined.Value;

		RuntimeContext currentContext = currentContext();
		StatementVisitorImpl visitor = currentContext.getStatementVisitor();
		// new lexical env
		Scope newDeclEnv = LexicalEnvironment.newDeclarativeEnvironment(scope);

		for (int i = 0; i < parameterList.size(); i++) {
			Reference mutableBinding = newDeclEnv.getBindings()
					.createMutableBinding(parameterList.get(i), false);
			mutableBinding.setValue(Reference.getValue(args[i]));
		}

		for (AstNode astNode : functionBody.getAstNodes()) {
			Statement statement = (Statement) astNode;
			currentContext.enter(statement, newDeclEnv, thisObj);
			statement.accept(visitor);
			if (!currentContext.exit()) {
				// called return statement
				break;
			}
		}
		returnValue = currentContext.currentExecutionContext().getCompletion()
				.getValue();

		return returnValue;// returnValue;
	}

	@Override
	public Scriptable construct(Scope scope, Object[] args) {
		RuntimeContext currentContext = currentContext();
		StatementVisitorImpl visitor = currentContext.getStatementVisitor();

		ScriptableObject scriptableObject = new ScriptableObject();
		// set prototype
		scriptableObject.setPrototype(getPrototype());
		// set constructor property

		// create new scope
		Scope newDeclEnv = LexicalEnvironment.newDeclarativeEnvironment(scope);

		// execute statements
		List<AstNode> astNodes = getFunctionBody().getAstNodes();
		for (AstNode astNode : astNodes) {
			Statement statement = (Statement) astNode;
			currentContext.enter(statement, newDeclEnv, scriptableObject);
			statement.accept(visitor);
			if (!currentContext.exit()) {
				// called return statement
				break;
			}

		}

		return scriptableObject;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @return the parameterList
	 */
	@Override
	public List<String> getParameterList() {
		return parameterList;
	}

	/**
	 * @return the sourceElements
	 */
	public AstNodeList getFunctionBody() {
		return functionBody;
	}

	/**
	 * @return the scope
	 */
	public Scope getScope() {
		return scope;
	}

	@Override
	public Object hasInstance(Object obj) {
		if (obj instanceof Scriptable) {
			Object o = getPrototype();
			if (o instanceof Scriptable) {
				Scriptable v = (Scriptable) obj;
				while (v != null) {
					v = v.getPrototype();
					if (v == o) {
						return true;
					}
				}
				return false;
			}
			throw new TypeErrorException();
		}
		return false;
	}

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		List<Object> values = new ArrayList<Object>(
				Arrays.asList(actualParameters));
		if (actualParameters.length < parameterList.size()) {
			int diff = parameterList.size() - actualParameters.length;
			for (int i = 0; i < diff; i++) {
				values.add(Undefined.Value);
			}
		}
		return values.toArray(new Object[] {});
	}

}
