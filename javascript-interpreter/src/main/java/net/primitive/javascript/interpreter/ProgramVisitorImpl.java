package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.ExecutionContext.currentContext;

import java.util.Iterator;
import java.util.List;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Program;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final Scriptable scope;
	private final ExecutionContext context;

	public ProgramVisitorImpl(ExecutionContext context,
			Scriptable scriptableObject) {
		this.context = context;
		this.scope = scriptableObject;
	}

	@Override
	public Scriptable getScope() {
		return scope;
	}

	@Override
	public void visit(Program program) {
		List<Statement> sourceElements = program.getSourceElements();
		Iterator<Statement> iterator = sourceElements.iterator();
		StatementVisitorImpl visitor = currentContext().getStatementVisitor();
		while (true) {
			Statement statement = context.currentStatement();
			if (statement == null && iterator.hasNext()) {
				context.enter(iterator.next());
				continue;
			} else if (statement == null && !iterator.hasNext()) {
				break;
			}
			statement.accept(visitor);
			context.exitStatement();
		}
	}
}
