package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Function;
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
		Object result1 = result;

		binaryExpression.getOp2().accept(this);
		Object result2 = result;

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
		ScriptableObjectProperty property = getScope().getProperty(
				identifier.getIdentfierName());
		result = property.getValue();
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
		property.setValue(result);
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

		result = unaryExpression.getOperator().operator(result);
	}

	@Override
	public void visitMemberExpression(MemberExpression memberExpression) {
		memberExpression.getExpression().accept(this);
		List<Expression> suffixes = memberExpression.getExpresionSuffixes();
		ExpressionVisitorImpl visitor = context.getExpressionVisitor();
		if (suffixes != null) {
			for (Expression suffix : suffixes) {
				if (result instanceof Scriptable) {
					context.enter((Scriptable) result);
					suffix.accept(visitor);
					context.exitScope();
				}
				result = visitor.getResult();
			}
		}
	}

	@Override
	public void visitCallExpression(CallExpression callExpression) {
		Expression memberExpression = callExpression.getMemberExpression();
		memberExpression.accept(this);
		Function function = (Function) result;
		result = function.call(getScope(), getScope(), null);
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

	@Override
	public void visitThis(This this1) {
		// TODO Auto-generated method stub
		
	}

}
