package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.StatementBlock;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;

public interface StatementVisitor extends SourceElementVisitor {

	void visitVariableDeclaration(VariableDeclaration variableDeclaration);

	void visitFunctionDeclaration(FunctionDeclaration functionDeclaration);

	void visitExpressionStatement(ExpressionStatement expressionStatement);

	void visitIfStatement(IfStatement ifStatement);

	void visitStatementBlock(StatementBlock statementBlock);

	void visitWhileStatement(WhileStatement whileStatement);

	void visitForStatement(ForStatement forStatement);

}
