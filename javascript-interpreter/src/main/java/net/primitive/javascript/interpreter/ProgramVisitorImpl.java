package net.primitive.javascript.interpreter;

import java.util.Iterator;
import java.util.List;

import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

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
			ExecutionContext executionContext = context
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
