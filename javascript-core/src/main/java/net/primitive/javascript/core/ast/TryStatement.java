package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	private final Statement blockStatement;
	private final Statement catchStatement;
	private final Statement finallyStatement;

	public TryStatement(Statement blockStatement, Statement catchStatement,
			Statement finallyStatement) {
		this.blockStatement = blockStatement;
		this.catchStatement = catchStatement;
		this.finallyStatement = finallyStatement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitTryStatement(this);
	}

	/**
	 * @return the blockStatement
	 */
	public Statement getBlockStatement() {
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
	public Statement getFinallyStatement() {
		return finallyStatement;
	}

}
