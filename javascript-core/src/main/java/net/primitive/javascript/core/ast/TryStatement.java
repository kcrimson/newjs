package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	@Getter private final AstNodeList blockStatement;
	@Getter private final Statement catchStatement;
	@Getter private final AstNodeList finallyStatement;

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

}
