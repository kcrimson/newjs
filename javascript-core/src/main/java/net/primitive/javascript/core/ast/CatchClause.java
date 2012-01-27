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

import lombok.Getter;

public class CatchClause extends Statement {

	@Getter private final String identifier;
	@Getter private final Statement[] statements;

	public CatchClause(String identifier, AstNodeList astNode) {
		this.identifier = identifier;
		this.statements = astNode.getAstNodes().toArray(new Statement[] {});
		astNode.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitCatchClause(this);
	}

}
