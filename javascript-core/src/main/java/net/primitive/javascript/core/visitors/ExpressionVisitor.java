package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.AssignmentExpression;
import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.CallExpression;
import net.primitive.javascript.core.ast.ConditionalExpression;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.FunctionExpression;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.MemberExpression;
import net.primitive.javascript.core.ast.ObjectLiteral;
import net.primitive.javascript.core.ast.This;
import net.primitive.javascript.core.ast.UnaryExpression;
import net.primitive.javascript.core.ast.WrappedExpression;

public interface ExpressionVisitor {

	void visitBinaryExpression(BinaryExpression binaryExpression);

	void visitLiteral(Literal literal);

	void visitWrappedExpression(WrappedExpression wrappedExpression);

	void visitIdentifier(Identifier identifier);

	void visitAssignmentExpression(AssignmentExpression assignmentExpression);

	void visitLeftHandSideExpression(Expression leftHandSideExpression);

	void visitUnaryExpression(UnaryExpression unaryExpression);

	void visitMemberExpression(MemberExpression memberExpression);

	void visitCallExpression(CallExpression callExpression);

	void visitObjectLiteral(ObjectLiteral objectLiteral);

	void visitFunctionExpression(FunctionExpression functionExpression);

	void visitThis(This this1);

	void visitConditionalExpression(ConditionalExpression conditionalExpression);

}
