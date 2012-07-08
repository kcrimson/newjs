/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.core.ast;

import net.primitive.javascript.core.BinaryOperator;

public final class AssignmentExpression extends Expression {

	private final Expression leftHandSideExpression;
	private final BinaryOperator assignmentOperator;
	private final Expression rightHandSideExpression;

	public AssignmentExpression(Expression expression, BinaryOperator assignmentOperator, Expression expression2) {
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
	public BinaryOperator getAssignmentOperator() {
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
