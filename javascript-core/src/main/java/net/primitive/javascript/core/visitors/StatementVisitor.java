package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.StatementBlock;
import net.primitive.javascript.core.ast.ThrowStatement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.VariableStatement;
import net.primitive.javascript.core.ast.WhileStatement;

public interface StatementVisitor {

	void visitVariableDeclaration(VariableDeclaration variableDeclaration);

	void visitFunctionDeclaration(FunctionDeclaration functionDeclaration);

	void visitExpressionStatement(ExpressionStatement expressionStatement);

	void visitIfStatement(IfStatement ifStatement);

	void visitStatementBlock(StatementBlock statementBlock);

	void visitWhileStatement(WhileStatement whileStatement);

	void visitForStatement(ForStatement forStatement);

	void visitReturnStatement(ReturnStatement returnStatement);

	void visitThrowStatement(ThrowStatement throwStatement);

	void visitCatchClause(CatchClause catchClause);

	void visitTryStatement(TryStatement tryStatement);

	void visitVariableStatement(VariableStatement variableStatement);

}
