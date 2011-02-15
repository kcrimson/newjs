package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.FunctionDeclaration;
import net.primitive.javascript.core.ast.VariableDeclaration;


public interface StatementVisitor extends SourceElementVisitor {

	void visitVariableDeclaration(VariableDeclaration variableDeclaration);

	void visitFunctionDeclaration(FunctionDeclaration functionDeclaration);

}
