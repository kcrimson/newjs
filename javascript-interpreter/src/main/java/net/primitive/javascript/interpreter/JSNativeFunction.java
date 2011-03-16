package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.ExecutionContext.currentContext;

import java.util.List;

import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.SourceElement;
import net.primitive.javascript.core.ast.Statement;

public class JSNativeFunction extends ScriptableObject implements Function {

	private final String functionName;
	private final List<String> parameterList;
	private final SourceElement[] sourceElements;

	public JSNativeFunction(String functionName, List<String> parameterList,
			SourceElement[] sourceElements) {
		this.functionName = functionName;
		this.parameterList = parameterList;
		this.sourceElements = sourceElements;
	}

	@Override
	public Object call(Scriptable scope, Scriptable thisObj, Object[] args) {

		ExecutionContext  currentContext = currentContext();
		StatementVisitorImpl visitor = currentContext.getStatementVisitor();
		for (int i = 0; i < sourceElements.length; i++) {
			((Statement) sourceElements[i]).accept(visitor);
		}
		Object returnValue = currentContext.returnValue();
		return returnValue;
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
	public SourceElement[] getSourceElements() {
		return sourceElements;
	}

}
