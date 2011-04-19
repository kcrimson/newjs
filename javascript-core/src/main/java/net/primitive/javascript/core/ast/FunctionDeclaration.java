package net.primitive.javascript.core.ast;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import lombok.Getter;
import net.primitive.javascript.core.visitors.StatementVisitor;

public final class FunctionDeclaration extends Statement {

	@Getter private final String functionName;
	@Getter private final List<String> parameterList;
	@Getter private final AstNodeList functionBody;

	public FunctionDeclaration(String functionName, List<String> parameterList,
			AstNodeList functionBody) {
		this.functionName = functionName;
		this.parameterList = unmodifiableList(parameterList);
		this.functionBody = functionBody;
	}

	@Override
	public void accept(StatementVisitor visitor) {
		visitor.visitFunctionDeclaration(this);
	}

}
