package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.visitors.ExpressionVisitor;

public final class AssignmentExpression extends Expression {

	private final Expression leftHandSideExpression;
	private final AssignmentOperator assignmentOperator;
	private final Expression rightHandSideExpression;

	public AssignmentExpression(Expression expression,
			AssignmentOperator assignmentOperator, Expression expression2) {
		this.leftHandSideExpression = expression;
		this.assignmentOperator = assignmentOperator;
		this.rightHandSideExpression = expression2;
	}

	/**
	 * @return the leftHandSideExpression
	 */
	public Expression getLeftHandSideExpression() {
		return leftHandSideExpression;
	}

	/**
	 * @return the assignmentOperator
	 */
	public AssignmentOperator getAssignmentOperator() {
		return assignmentOperator;
	}

	/**
	 * @return the rightHandSideExpression
	 */
	public Expression getRightHandSideExpression() {
		return rightHandSideExpression;
	}

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitAssignmentExpression(this);
	}

}
