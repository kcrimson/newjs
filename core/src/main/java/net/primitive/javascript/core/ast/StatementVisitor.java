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


public interface StatementVisitor {

	void visitVariableDeclaration(VariableDeclaration variableDeclaration);

	void visitFunctionDeclaration(FunctionDeclaration functionDeclaration);

	void visitExpressionStatement(ExpressionStatement expressionStatement);

	void visitIfStatement(IfStatement ifStatement);

	void visitWhileStatement(WhileStatement whileStatement);

	void visitForStatement(ForStatement forStatement);

	void visitReturnStatement(ReturnStatement returnStatement);

	void visitThrowStatement(ThrowStatement throwStatement);

	void visitCatchClause(CatchClause catchClause);

	void visitTryStatement(TryStatement tryStatement);

	void visitBreakStatement(BreakStatement breakStatement);

	void visitDoWhileStatement(DoWhileStatement doWhileStatement);

	void visitForInStatement(ForInStatement forInStatement);

	void visitSwitchStatement(SwitchStatement switchStatement);

	void visitLabelledStatement(LabelledStatement labelledStatement);

}
