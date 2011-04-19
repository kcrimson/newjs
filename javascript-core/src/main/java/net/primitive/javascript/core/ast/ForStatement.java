package net.primitive.javascript.core.ast;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public class ForStatement extends Statement {

	@Getter private final Statement initializeStatement;
	@Getter private final Expression incrementExpression;
	@Getter private final Expression testExpression;
	@Getter private final AstNodeList statement;

	public ForStatement(Statement initializeStatement,
			Expression incrementExpression, Expression testExpression,
			AstNode statement) {
		this.initializeStatement = initializeStatement;
		initializeStatement.setParentNode(this);
		this.incrementExpression = incrementExpression;
		this.testExpression = testExpression;
		this.statement = AstNodeList.wrapAstNode(statement);
		statement.setParentNode(this);
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitForStatement(this);
	}

}
