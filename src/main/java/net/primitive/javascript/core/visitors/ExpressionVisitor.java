package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.WrappedExpression;


public interface ExpressionVisitor extends SourceElementVisitor {

	void visitBinaryExpression(BinaryExpression binaryExpression);

	void visitLiteral(Literal literal);

	void visitWrappedExpression(WrappedExpression wrappedExpression);

}
