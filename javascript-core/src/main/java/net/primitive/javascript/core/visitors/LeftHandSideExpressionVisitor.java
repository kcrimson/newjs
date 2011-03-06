package net.primitive.javascript.core.visitors;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.MemberExpression;

public interface LeftHandSideExpressionVisitor {

	void visitIdentifier(Identifier identifier);

	Scriptable getScope();

	Object getResult();

	void visitMemberExpression(MemberExpression memberExpression);

}
