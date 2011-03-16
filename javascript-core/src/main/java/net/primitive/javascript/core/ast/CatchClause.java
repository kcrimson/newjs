package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class CatchClause extends Statement {

	private final String identifier;
	private final Statement statement;

	public CatchClause(String identifier, Statement statement) {
		this.identifier = identifier;
		this.statement = statement;
		statement.setParentAstNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitCatchClause(this);
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return statement;
	}

}
