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

public class IfStatement extends Statement {

	@Getter
	private final Expression expression;
	@Getter
	private AstNodeList ifStatement;
	@Getter
	private AstNodeList elseStatement;

	public IfStatement(Expression expression, AstNode ifstmnt, AstNode elsestmnt) {
		this.expression = expression;

		if (ifstmnt instanceof AstNodeList) {
			this.ifStatement = (AstNodeList) ifstmnt;
		} else if (ifstmnt != null) {
			this.ifStatement = new AstNodeList();
			this.ifStatement.addAstNode(ifstmnt);
		}
		this.ifStatement.setParentNode(this);

		if (elsestmnt instanceof AstNodeList) {
			this.elseStatement = (AstNodeList) elsestmnt;
		} else if (elsestmnt != null) {
			this.elseStatement = new AstNodeList();
			this.elseStatement.addAstNode(elsestmnt);
		}

		if (this.elseStatement != null) {
			this.elseStatement.setParentNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitIfStatement(this);
	}

}
