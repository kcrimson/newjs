package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.currentContext;

import java.util.Iterator;
import java.util.List;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final Scriptable scope;
	private final RuntimeContext context;

	public ProgramVisitorImpl(RuntimeContext context,
			Scriptable scriptableObject) {
		this.context = context;
		this.scope = scriptableObject;
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
