/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
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

import lombok.Getter;

public class ForStatement extends Statement {

	@Getter
	private final AstNode initializeExpression;
	@Getter
	private final Expression incrementExpression;
	@Getter
	private final Expression testExpression;
	@Getter
	private final AstNode statements;

	public ForStatement(AstNode initializeExpression, Expression incrementExpression,
			Expression testExpression, AstNode statements) {
				this.initializeExpression = initializeExpression;
				this.incrementExpression = incrementExpression;
				this.testExpression = testExpression;
				this.statements = statements;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForStatement(this);
	}

}
