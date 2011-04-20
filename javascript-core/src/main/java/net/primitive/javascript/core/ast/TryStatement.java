package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	private final AstNodeList blockStatement;
	private Statement catchStatement;
	private final AstNodeList finallyStatement;

	public TryStatement(AstNodeList statements, Statement catchClause,
			AstNodeList finallyClause) {
		blockStatement = statements;
		catchStatement = catchClause;
		finallyStatement = finallyClause;
	}

	public TryStatement(AstNodeList statements, AstNodeList finallyClause) {
		blockStatement = statements;
		finallyStatement = finallyClause;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitTryStatement(this);
	}

	/**
	 * @return the blockStatement
	 */
	public AstNodeList getBlockStatement() {
		return blockStatement;
	}

	/**
	 * @return the catchStatement
	 */
	public Statement getCatchStatement() {
		return catchStatement;
	}

	/**
	 * @return the finallyStatement
	 */
	public AstNodeList getFinallyStatement() {
		return finallyStatement;
	}

}
