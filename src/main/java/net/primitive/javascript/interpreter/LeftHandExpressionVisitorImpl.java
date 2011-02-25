package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObjectProperty;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.LeftHandSideExpression;
import net.primitive.javascript.core.ast.MemberExpression;
import net.primitive.javascript.core.visitors.LeftHandSideExpressionVisitor;

public class LeftHandExpressionVisitorImpl implements
		LeftHandSideExpressionVisitor {

	private final Scriptable scope;

	private ScriptableObjectProperty result;

	public LeftHandExpressionVisitorImpl(Scriptable scope) {
		this.scope = scope;
	}

	/**
	 * @return the scope
	 */
	@Override
	public Scriptable getScope() {
		return scope;
	}

	@Override
	public void visitIdentifier(Identifier identifier) {
		String identfierName = identifier.getIdentfierName();
		result = getScope().getProperty(identfierName);
	}

	/**
	 * @return the result
	 */
	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void visitMemberExpression(MemberExpression memberExpression) {
		LeftHandSideExpression expression = (LeftHandSideExpression) memberExpression.getExpression();
		expression.accept(this);
	}

}
