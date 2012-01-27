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

import java.util.Iterator;
import java.util.List;

import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.ProgramVisitor;
import net.primitive.javascript.core.ast.Statement;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final RuntimeContext context;

	public ProgramVisitorImpl(RuntimeContext context) {
		this.context = context;
	}

	@Override
	public void visit(Program program) {
		List<AstNode> nodes = program.getAstNodes();
		Iterator<AstNode> iterator = nodes.iterator();
		StatementVisitorImpl visitor = context.getStatementVisitor();

		while (true) {
			StatementExecutionContext executionContext = context
					.currentExecutionContext();
			if (executionContext == null && iterator.hasNext()) {
				context.enter((Statement) iterator.next());
				continue;
			} else if (executionContext == null && !iterator.hasNext()) {
				break;
			}
			executionContext.accept(visitor);
			context.exit();
		}
	}
}
