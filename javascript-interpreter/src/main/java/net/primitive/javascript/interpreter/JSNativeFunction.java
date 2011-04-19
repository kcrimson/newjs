package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.currentContext;

import java.util.List;

import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.AstNodeList;

public class JSNativeFunction extends ScriptableObject implements Function {

	private final String functionName;
	private final List<String> parameterList;
	private final AstNodeList functionBody;

	public JSNativeFunction(String functionName, List<String> parameterList,
			AstNodeList functionBody) {
		this.functionName = functionName;
		this.parameterList = parameterList;
		this.functionBody = functionBody;
	}

	@Override
	public Object call(Scriptable scope, Scriptable thisObj, Object[] args) {

		RuntimeContext  currentContext = currentContext();
		StatementVisitorImpl visitor = currentContext.getStatementVisitor();
//		for (int i = 0; i < functionBody.length; i++) {
//			((Statement) functionBody[i]).accept(visitor);
//		}
//		Object returnValue = currentContext.returnValue();
		return null;//returnValue;
	}

	@Override
	public Scriptable construct(Scriptable scope, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @return the parameterList
	 */
	public List<String> getParameterList() {
		return parameterList;
	}

	/**
	 * @return the sourceElements
	 */
	public AstNodeList getFunctionBody() {
		return functionBody;
	}

}
