package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Property;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.AssignmentExpression;
import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.WrappedExpression;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class ExpressionVisitorImpl implements ExpressionVisitor {

	private final Scriptable scope;
	private Object result;

	protected ExpressionVisitorImpl(Scriptable scope) {
		super();
		this.scope = scope;
	}

	@Override
	public Scriptable getScope() {
		return scope;
	}

	public Object getResult() {
		return result;
	}

	@Override
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		ExpressionVisitorImpl op1visitor = new ExpressionVisitorImpl(scope);
		binaryExpression.getOp1().accept(op1visitor);
		Object result1 = op1visitor.getResult();

		ExpressionVisitorImpl op2visitor = new ExpressionVisitorImpl(scope);
		binaryExpression.getOp2().accept(op2visitor);
		Object result2 = op2visitor.getResult();

		result = binaryExpression.getOperator().operator(result1, result2);
	}

	@Override
	public void visitLiteral(Literal literal) {
		result = literal.getValue();
	}

	@Override
	public void visitWrappedExpression(WrappedExpression wrappedExpression) {
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		wrappedExpression.getExpression1().accept(visitor);
		result = visitor.getResult();
	}

	@Override
	public void visitIdentifier(Identifier identifier) {
		Property property = getScope().getProperty(
				identifier.getIdentfierName());
		result = property;
	}

	@Override
	public void visitAssignmentExpression(
			AssignmentExpression assignmentExpression) {
		ExpressionVisitorImpl rightVisitor = new ExpressionVisitorImpl(
				getScope());
		assignmentExpression.getRightHandSideExpression().accept(rightVisitor);
		Object rightValue = rightVisitor.getResult();

		ExpressionVisitorImpl leftVisitor = new ExpressionVisitorImpl(
				getScope());
		assignmentExpression.getLeftHandSideExpression().accept(leftVisitor);
		Property property = (Property) leftVisitor.getResult();
		property.setValue(rightValue);
	}

}
