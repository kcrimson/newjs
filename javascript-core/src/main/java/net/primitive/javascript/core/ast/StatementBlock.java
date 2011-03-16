package net.primitive.javascript.core.ast;

import java.util.List;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class StatementBlock extends Statement {

	private final Statement[] statements;

	public StatementBlock(List<Statement> statements) {
		this.statements = statements.toArray(new Statement[] {});
		for (Statement statement : statements) {
			statement.setParentAstNode(this);
		}
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitStatementBlock(this);
	}

	public Statement[] getStatements() {
		return statements;
	}

}
