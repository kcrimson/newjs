package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class LabelledStatement extends Statement {

	private final String identifier;
	private final AstNode statement;

	public LabelledStatement(String identifier, AstNode astNode) {
		this.identifier = identifier;
		this.statement = astNode;
		astNode.setParentNode(this);
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
	public AstNode getStatement() {
		return statement;
	}

}
