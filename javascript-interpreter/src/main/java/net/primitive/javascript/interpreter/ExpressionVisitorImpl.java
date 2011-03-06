package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ScriptableObjectProperty;
import net.primitive.javascript.core.ast.AssignmentExpression;
import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.CallExpression;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.FunctionExpression;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.LeftHandSideExpression;
import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.MemberExpression;
import net.primitive.javascript.core.ast.NameValuePair;
import net.primitive.javascript.core.ast.ObjectLiteral;
import net.primitive.javascript.core.ast.UnaryExpression;
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
		ScriptableObjectProperty property = getScope().getProperty(
				identifier.getIdentfierName());
		result = property.getValue();
	}

	@Override
	public void visitAssignmentExpression(
			AssignmentExpression assignmentExpression) {
		ExpressionVisitorImpl rightVisitor = new ExpressionVisitorImpl(
				getScope());
		assignmentExpression.getRightHandSideExpression().accept(rightVisitor);
		Object rightValue = rightVisitor.getResult();

		LeftHandExpressionVisitorImpl leftVisitor = new LeftHandExpressionVisitorImpl(
				getScope());
		((LeftHandSideExpression) assignmentExpression
				.getLeftHandSideExpression()).accept(leftVisitor);
		ScriptableObjectProperty property = (ScriptableObjectProperty) leftVisitor
				.getResult();
		property.setValue(rightValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.primitive.javascript.core.visitors.ExpressionVisitor#
	 * visitLeftHandSideExpression
	 * (net.primitive.javascript.core.ast.LeftHandSideExpression)
	 */
	@Override
	public void visitLeftHandSideExpression(
			LeftHandSideExpression leftHandSideExpression) {
		LeftHandExpressionVisitorImpl visitor = new LeftHandExpressionVisitorImpl(
				getScope());
		leftHandSideExpression.accept(visitor);

		result = visitor.getResult();

	}

	@Override
	public void visitUnaryExpression(UnaryExpression unaryExpression) {

		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		unaryExpression.getOperand().accept(visitor);

		result = unaryExpression.getOperator().operator(visitor.result);
	}

	@Override
	public void visitMemberExpression(MemberExpression memberExpression) {
		ExpressionVisitorImpl visitor = new ExpressionVisitorImpl(getScope());
		memberExpression.getExpression().accept(visitor);
		result = visitor.getResult();
		List<Expression> suffixes = memberExpression.getExpresionSuffixes();
		if (suffixes != null) {
			for (Expression suffix : suffixes) {
				if (result instanceof Scriptable)
					visitor = new ExpressionVisitorImpl((Scriptable) result);
				suffix.accept(visitor);
				result = visitor.getResult();
			}
		}
	}

	@Override
	public void visitCallExpression(CallExpression callExpression) {
		Expression memberExpression = callExpression.getMemberExpression();
		memberExpression.accept(this);
		JSNativeFunction fun = (JSNativeFunction) result;
		fun.call(null, getScope(), getScope(), null);
	}

	@Override
	public void visitObjectLiteral(ObjectLiteral objectLiteral) {
		List<NameValuePair> nameValuePairs = objectLiteral.getNameValuePairs();
		ScriptableObject scriptableObject = new ScriptableObject();
		for (NameValuePair pair : nameValuePairs) {
			pair.getValue().accept(this);
			scriptableObject.put((String) pair.getName(), null, result);
		}
		result = scriptableObject;
	}

	@Override
	public void visitFunctionExpression(FunctionExpression functionExpression) {
		result = new JSNativeFunction(functionExpression.getFunctionName(),
				functionExpression.getParameterList(),
				functionExpression.getSourceElements());
	}

}
