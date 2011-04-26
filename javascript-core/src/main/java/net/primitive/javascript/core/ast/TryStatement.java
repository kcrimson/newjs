package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class TryStatement extends Statement {

	@Getter
	private final AstNodeList blockStatement;
	@Getter
	private Statement catchStatement;
	@Getter
	private final AstNodeList finallyStatement;

	public TryStatement(AstNodeList blockstatement, AstNodeList finallystatement) {
		blockStatement = blockstatement;
		finallyStatement = finallystatement;
	}

	public TryStatement(AstNodeList blockstatement, Statement catchstatement,
			AstNodeList finallystatement) {
		blockStatement = blockstatement;
		catchStatement = catchstatement;
		finallyStatement = finallystatement;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitTryStatement(this);
	}

}
