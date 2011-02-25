package net.primitive.javascript.interpreter;

import java.util.List;

import net.primitive.javascript.core.Context;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.ast.SourceElement;

public class JSNativeFunction extends ScriptableObject implements Function {

	public JSNativeFunction(String functionName, List<String> parameterList,
			List<SourceElement> sourceElements) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj,
			Object[] args) {
		// TODO Auto-generated method stub
		System.out.println("elou");
		return null;
	}

	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
