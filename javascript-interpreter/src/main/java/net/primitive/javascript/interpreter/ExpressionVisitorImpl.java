package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.AssignmentExpression;
import net.primitive.javascript.core.ast.BinaryExpression;
import net.primitive.javascript.core.ast.CallExpression;
import net.primitive.javascript.core.ast.ConditionalExpression;
import net.primitive.javascript.core.ast.Expression;
import net.primitive.javascript.core.ast.FunctionExpression;
import net.primitive.javascript.core.ast.Identifier;
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
	private final RuntimeContext context;

	protected ExpressionVisitorImpl(RuntimeContext context) {
		super();
		this.context = context;
	}

	public Object getResult() {
		return result;
	}

	@Override
	public void visitBinaryExpression(BinaryExpression binaryExpression) {
		binaryExpression.getOp1().accept(this);
		Object result1 = Reference.getValue(result);

		binaryExpression.getOp2().accept(this);
		Object result2 = Reference.getValue(result);

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

		result = LexicalEnvironment.getIdentifierReference(context
				.currentExecutionContext().getLexicalEnvironment(), identifier
				.getIdentfierName());
	}

	@Override
	public void visitAssignmentExpression(
			AssignmentExpression assignmentExpression) {
		assignmentExpression.getRightHandSideExpression().accept(this);
		Object value = result;
		ExpressionVisitor leftVisitor = context.getExpressionVisitor();
		assignmentExpression.getLeftHandSideExpression().accept(leftVisitor);
		Reference.putValue(result, value);
		// property.setValue(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.primitive.javascript.core.visitors.ExpressionVisitor#
	 * visitLeftHandSideExpression
	 * (net.primitive.javascript.core.ast.LeftHandSideExpression)
	 */
	@Override
	public void visitLeftHandSideExpression(Expression leftHandSideExpression) {
		ExpressionVisitor visitor = context.getExpressionVisitor();
		leftHandSideExpression.accept(visitor);

		// result = visitor.getResult();

	}

	@Override
	public void visitUnaryExpression(UnaryExpression unaryExpression) {

		unaryExpression.getOperand().accept(this);

		result = unaryExpression.getOperator().operator(
				Reference.getValue(result));
	}

	@Override
	public void visitMemberExpression(MemberExpression memberExpression) {
		memberExpression.getExpression().accept(this);

		List<Expression> suffixes = memberExpression.getExpresionSuffixes();
		if (suffixes != null && suffixes.size() > 0) {
			Object baseReference = result;
			Object baseValue = null;
			for (Expression suffix : suffixes) {
				String propertyNameString;
				if (Identifier.class.equals(suffix.getClass())) {
					propertyNameString = ((Identifier) suffix)
							.getIdentfierName();
				} else {
					suffix.accept(this);
					Object propertyNameReference = result;
					Object propertyNameValue = Reference
							.getValue(propertyNameReference);
					propertyNameString = Convertions
							.toString(propertyNameValue);
				}
				baseValue = Reference.getValue(baseReference);

				baseReference = new Reference(baseValue, propertyNameString);
			}
			result = baseReference;
		}
	}

	@Override
	public void visitCallExpression(CallExpression callExpression) {
		Expression memberExpression = callExpression.getMemberExpression();
		memberExpression.accept(this);
		PropertyDescriptor result2 = (PropertyDescriptor) result;
		// Scriptable thisObj = result2.getScope();
		ScriptableObject scope = new ScriptableObject();
		// scope.setParentScope(thisObj);
		Function function = (Function) getValue(result2);
		// context.enter(scope);
		// result = function.call(scope, thisObj, null);
		// context.exitScope();
	}

	@Override
	public void visitObjectLiteral(ObjectLiteral objectLiteral) {
		List<NameValuePair> nameValuePairs = objectLiteral.getNameValuePairs();
		ScriptableObject scriptableObject = new ScriptableObject();
		for (NameValuePair pair : nameValuePairs) {
			pair.getValue().accept(this);
			scriptableObject.put((String) pair.getName(), getValue(result));
		}
		result = scriptableObject;
	}

	@Override
	public void visitFunctionExpression(FunctionExpression functionExpression) {
		result = new JSNativeFunction(functionExpression.getFunctionName(),
				functionExpression.getParameterList(),
				functionExpression.getFunctionBody());
	}

	@Override
	public void visitThis(This this1) {
		// result = context.currentScope();
	}

	@Override
	public void visitConditionalExpression(
			ConditionalExpression conditionalExpression) {
		System.out.println("leva cipa");
	}

	private static Object getValue(Object object) {
		if (object instanceof PropertyDescriptor) {
			return ((PropertyDescriptor) object).getValue();
		}
		return object;
	}

}
