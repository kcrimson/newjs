package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

public class FunctionDeclaration extends SourceElement {

	private final String functionName;
	private final List<String> parameterList;
	private final List<SourceElement> sourceElements;

	public FunctionDeclaration(String functionName, List<String> parameterList,
			List<SourceElement> sourceElements) {
		this.functionName = functionName;
		this.parameterList = unmodifiableList(parameterList);
		this.sourceElements = unmodifiableList(sourceElements);
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
