package net.primitive.javascript.classgen;

import net.primitive.javascript.core.ast.BreakStatement;
import net.primitive.javascript.core.ast.CatchClause;
import net.primitive.javascript.core.ast.DoWhileStatement;
import net.primitive.javascript.core.ast.ExpressionStatement;
import net.primitive.javascript.core.ast.ForInStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.IfStatement;
import net.primitive.javascript.core.ast.ReturnStatement;
import net.primitive.javascript.core.ast.StatementVisitor;
import net.primitive.javascript.core.ast.SwitchStatement;
import net.primitive.javascript.core.ast.ThrowStatement;
import net.primitive.javascript.core.ast.TryStatement;
import net.primitive.javascript.core.ast.VariableDeclaration;
import net.primitive.javascript.core.ast.WhileStatement;

public class StatementVisitorImpl implements StatementVisitor {

	@Override
	public void visitVariableDeclaration(VariableDeclaration variableDeclaration) {
		throw new UnsupportedOperationException("visitVariableDeclaration");
	}

	@Override
	public void visitFunctionDeclaration(FunctionDeclaration functionDeclaration) {
		throw new UnsupportedOperationException("visitFunctionDeclaration");
	}

	@Override
	public void visitExpressionStatement(ExpressionStatement expressionStatement) {
		throw new UnsupportedOperationException("visitExpressionStatement");
	}

	@Override
	public void visitIfStatement(IfStatement ifStatement) {
		throw new UnsupportedOperationException("visitIfStatement");
	}

	@Override
	public void visitWhileStatement(WhileStatement whileStatement) {
		throw new UnsupportedOperationException("visitWhileStatement");
	}

	@Override
	public void visitForStatement(ForStatement forStatement) {
		throw new UnsupportedOperationException("visitForStatement");
	}

	@Override
	public void visitReturnStatement(ReturnStatement returnStatement) {
		throw new UnsupportedOperationException("visitReturnStatement");
	}

	@Override
	public void visitThrowStatement(ThrowStatement throwStatement) {
		throw new UnsupportedOperationException("visitThrowStatement");
	}

	@Override
	public void visitCatchClause(CatchClause catchClause) {
		throw new UnsupportedOperationException("visitCatchClause");
	}

	@Override
	public void visitTryStatement(TryStatement tryStatement) {
		throw new UnsupportedOperationException("visitTryStatement");
	}

	@Override
	public void visitBreakStatement(BreakStatement breakStatement) {
		throw new UnsupportedOperationException("visitBreakStatement");
	}

	@Override
	public void visitDoWhileStatement(DoWhileStatement doWhileStatement) {
		throw new UnsupportedOperationException("visitDoWhileStatement");
	}

	@Override
	public void visitForInStatement(ForInStatement forInStatement) {
		throw new UnsupportedOperationException("visitForInStatement");
	}

	@Override
	public void visitSwitchStatement(SwitchStatement switchStatement) {
		throw new UnsupportedOperationException("visitSwitchStatement");
	}

}
