package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Context;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.SourceElement;
import net.primitive.javascript.core.ast.Statement;

public class JSNativeFunction extends ScriptableObject implements Function {

	private final String functionName;
	private final List<String> parameterList;
	private final List<SourceElement> sourceElements;

	public JSNativeFunction(String functionName, List<String> parameterList,
			List<SourceElement> sourceElements) {
		this.functionName = functionName;
		this.parameterList = parameterList;
		this.sourceElements = sourceElements;
	}

	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj,
			Object[] args) {

		StatementVisitorImpl visitor = new StatementVisitorImpl(scope);

		for (SourceElement sourceElement : sourceElements) {
			((Statement) sourceElement).accept(visitor);
		}

		System.out.println("elou");
		return null;
	}

	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
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
	public List<SourceElement> getSourceElements() {
		return sourceElements;
	}

}
