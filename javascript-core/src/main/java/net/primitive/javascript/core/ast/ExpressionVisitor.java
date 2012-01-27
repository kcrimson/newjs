/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
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

	void visitNewExpression(NewExpression newExpression);

	void visitArguments(Arguments arguments);

	void visitCompoundAssignment(CompoundAssignment compoundAssignment);

	void visitArrayLiteral(ArrayLiteral arrayLiteral);

}
