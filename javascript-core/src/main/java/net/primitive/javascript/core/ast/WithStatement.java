package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class WithStatement extends Statement {

	@Getter private final Expression expression;
	@Getter private final AstNodeList statement;

	public WithStatement(Expression expression, AstNode astNode) {
		this.expression = expression;
		this.statement = AstNodeList.wrapAstNode(astNode);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		throw new UnsupportedOperationException("acceptWithStatement");
	}

}
