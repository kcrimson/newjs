package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.ExecutionContext.currentContext;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final Scriptable scope;

	public ProgramVisitorImpl(Scriptable scriptableObject) {
		this.scope = scriptableObject;
	}

	@Override
	public Scriptable getScope() {
		return scope;
	}

	@Override
	public void visit(Statement statement) {

		StatementVisitorImpl visitor = currentContext().getStatementVisitor();
		statement.accept(visitor);

	}
}
