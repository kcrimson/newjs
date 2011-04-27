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
import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	@Getter
	private final AstNodeList blockStatement;
	@Getter
	private Statement catchStatement;
	@Getter
	private final AstNodeList finallyStatement;

	public TryStatement(AstNodeList blockstatement, AstNodeList finallystatement) {
		blockStatement = blockstatement;
		finallyStatement = finallystatement;
	}

	public TryStatement(AstNodeList blockstatement, Statement catchstatement,
			AstNodeList finallystatement) {
		blockStatement = blockstatement;
		catchStatement = catchstatement;
		finallyStatement = finallystatement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitTryStatement(this);
	}

}
