package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.currentContext;

import java.util.List;

import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.AstNode;
import net.primitive.javascript.core.ast.AstNodeList;
import net.primitive.javascript.core.ast.Statement;

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

		Object returnValue = Undefined.Value;

		RuntimeContext currentContext = currentContext();
		StatementVisitorImpl visitor = currentContext.getStatementVisitor();
		// new lexical env
		LexicalEnvironment newDeclEnv = LexicalEnvironment
				.newDeclarativeEnvironment(currentContext
						.currentExecutionContext().getLexicalEnvironment());
		
		for(int i=0;i<parameterList.size();i++){
			Reference mutableBinding = newDeclEnv.getEnvironmentRecords().createMutableBinding(parameterList.get(i), false);
			mutableBinding.setValue(args[i]);
		}

		for (AstNode astNode : functionBody.getAstNodes()) {
			Statement statement = (Statement) astNode;
			currentContext.enter(statement, newDeclEnv, thisObj);
			statement.accept(visitor);
			if (!currentContext.exit()) {
				// called return statement
				break;
			}
		}
		returnValue = currentContext.currentExecutionContext().getCompletion()
				.getValue();

		return returnValue;// returnValue;
	}

	@Override
	public Scriptable construct(Scriptable scope, Object[] args) {
		// TODO Auto-generated method stub
		return new ScriptableObject();
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
