package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class LabelledStatement extends Statement {

	private final String identifier;
	private final Statement statement;

	public LabelledStatement(String identifier, Statement statement) {
		this.identifier = identifier;
		this.statement = statement;
		statement.setParentAstNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptLabelledStatement");
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
