package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	private final AstNodeList blockStatement;
	private final Statement catchStatement;
	private final AstNodeList finallyStatement;

	public TryStatement(AstNode astNode, AstNodeList astNodeList, Statement catchStatement,
			AstNodeList astNodeList2) {
		this.blockStatement = astNodeList;
		astNodeList.setParentNode(this);
		this.catchStatement = catchStatement;
		catchStatement.setParentNode(this);
		this.finallyStatement = astNodeList2;
		if (astNodeList2 != null) {
			astNodeList2.setParentNode(this);
		}
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
