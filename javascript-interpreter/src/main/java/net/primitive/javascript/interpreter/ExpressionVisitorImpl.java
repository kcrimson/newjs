package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ScriptableObjectProperty;
import net.primitive.javascript.core.ast.AssignmentExpression;
import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.CallExpression;
import net.primitive.javascript.core.ast.ConditionalExpression;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.FunctionExpression;
import net.primitive.javascript.core.ast.Identifier;
import net.primitive.javascript.core.ast.LeftHandSideExpression;
import net.primitive.javascript.core.ast.Literal;
import net.primitive.javascript.core.ast.MemberExpression;
import net.primitive.javascript.core.ast.NameValuePair;
import net.primitive.javascript.core.ast.ObjectLiteral;
import net.primitive.javascript.core.ast.This;
import net.primitive.javascript.core.ast.UnaryExpression;
import net.primitive.javascript.core.ast.WrappedExpression;
import net.primitive.javascript.core.visitors.ExpressionVisitor;

public class ExpressionVisitorImpl implements ExpressionVisitor {

	private Object result;
	private final ExecutionContext context;

	protected ExpressionVisitorImpl(ExecutionContext context) {
		super();
		this.context = context;
	}

	@Override
	public Scriptable getScope() {
		return context.currentScope();
	}

	public Object getResult() {
		return result;
	}

	@Override
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		binaryExpression.getOp1().accept(this);
		Object result1 = getValue(result);

		binaryExpression.getOp2().accept(this);
		Object result2 = getValue(result);

		result = binaryExpression.getOperator().operator(result1, result2);
	}

	@Override
	public void visitLiteral(Literal literal) {
		result = literal.getValue();
	}

	@Override
	public void visitWrappedExpression(WrappedExpression wrappedExpression) {
		wrappedExpression.getExpression1().accept(this);
	}

	@Override
	public void visitIdentifier(Identifier identifier) {

		// System.out.println("visitIndentifier " +
		// identifier.getIdentfierName());

		ScriptableObjectProperty property = getScope().getProperty(
				identifier.getIdentfierName());
		result = property;
	}

	@Override
	public void visitAssignmentExpression(
			AssignmentExpression assignmentExpression) {
		assignmentExpression.getRightHandSideExpression().accept(this);

		LeftHandExpressionVisitorImpl leftVisitor = new LeftHandExpressionVisitorImpl(
				getScope());
		((LeftHandSideExpression) assignmentExpression
				.getLeftHandSideExpression()).accept(leftVisitor);
		ScriptableObjectProperty property = (ScriptableObjectProperty) leftVisitor
				.getResult();
		property.setValue(getValue(result));
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

		unaryExpression.getOperand().accept(this);

		result = unaryExpression.getOperator().operator(getValue(result));
	}

	@Override
	public void visitMemberExpression(MemberExpression memberExpression) {
		memberExpression.getExpression().accept(this);
		List<Expression> suffixes = memberExpression.getExpresionSuffixes();
		ExpressionVisitorImpl visitor = context.getExpressionVisitor();
		if (suffixes != null && suffixes.size()>0) {
			Object value = null;
			for (Expression suffix : suffixes) {
				value = getValue(result);
				if (value instanceof Scriptable) {
					context.enter((Scriptable) value);
					suffix.accept(visitor);
					context.exitScope();
				}
				value = result;
			}
			result = value;
		}
	}

	@Override
	public void visitCallExpression(CallExpression callExpression) {
		Expression memberExpression = callExpression.getMemberExpression();
		memberExpression.accept(this);
		ScriptableObjectProperty result2 = (ScriptableObjectProperty) result;
		Scriptable thisObj = result2.getScope();
		ScriptableObject scope = new ScriptableObject();
		scope.setParentScope(thisObj);
		Function function = (Function) getValue(result2);
		context.enter(scope);
		result = function.call(scope, thisObj, null);
		context.exitScope();
	}

	@Override
	public void visitObjectLiteral(ObjectLiteral objectLiteral) {
		List<NameValuePair> nameValuePairs = objectLiteral.getNameValuePairs();
		ScriptableObject scriptableObject = new ScriptableObject();
		for (NameValuePair pair : nameValuePairs) {
			pair.getValue().accept(this);
			scriptableObject.put((String) pair.getName(), null, getValue(result));
		}
		result = scriptableObject;
	}

	@Override
	public void visitFunctionExpression(FunctionExpression functionExpression) {
		result = new JSNativeFunction(functionExpression.getFunctionName(),
				functionExpression.getParameterList(),
				functionExpression.getSourceElements());
	}

	@Override
	public void visitThis(This this1) {
		result = context.currentScope().getParentScope();
	}

	@Override
	public void visitConditionalExpression(
			ConditionalExpression conditionalExpression) {
		System.out.println("leva cipa");
	}

	private static Object getValue(Object object) {
		if (object instanceof ScriptableObjectProperty) {
			return ((ScriptableObjectProperty) object).getValue();
		}
		return object;
	}

}
