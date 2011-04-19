package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class CatchClause extends Statement {

	private final String identifier;
	private final AstNodeList statements;

	public CatchClause(String identifier, AstNodeList astNode) {
		this.identifier = identifier;
		this.statements = astNode;
		astNode.setParentNode(this);
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
	public AstNodeList getStatement() {
		return statements;
	}

}
