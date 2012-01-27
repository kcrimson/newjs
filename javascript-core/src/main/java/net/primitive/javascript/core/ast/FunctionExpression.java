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
package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import lombok.Getter;


public class FunctionExpression extends Expression {

	@Getter private final String functionName;
	@Getter private final List parameterList;
	@Getter private final AstNodeList functionBody;

	public FunctionExpression(String functionName, List parameterList,
			AstNodeList functionBody) {
		this.functionName = functionName;
		this.parameterList = unmodifiableList(parameterList);
		this.functionBody = functionBody;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitFunctionExpression(this);
	}

}
