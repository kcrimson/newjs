package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.visitors.ProgramVisitor;

public class ProgramVisitorImpl implements ProgramVisitor {

	private final Scriptable scriptableObject;

	public ProgramVisitorImpl(Scriptable scriptableObject) {
		this.scriptableObject = scriptableObject;
	}

	@Override
	public Scriptable getScope() {
		return scriptableObject;
	}

	@Override
	public void visit(Statement statement) {
		StatementVisitorImpl visitor = new StatementVisitorImpl(getScope());
		statement.accept(visitor);
	}
}
