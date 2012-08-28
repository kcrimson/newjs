package net.primitive.javascript.interpreter.utils;

import net.primitive.javascript.core.ast.DoWhileStatement;
import net.primitive.javascript.core.ast.ForStatement;
import net.primitive.javascript.core.ast.LabelledStatement;
import net.primitive.javascript.core.ast.Statement;
import net.primitive.javascript.core.ast.SwitchStatement;
import net.primitive.javascript.core.ast.WhileStatement;

public final class StatementUtil {
	
	/** default constructor for utility classes */
	private StatementUtil(){
	}
	
	public static boolean isSwitchStatement(Statement statement) {
		return isOfType(statement, SwitchStatement.class);
	}
	
	public static boolean isIterationStatement(Statement statement) {
		return isOfType(statement, WhileStatement.class) || isOfType(statement, DoWhileStatement.class) || isOfType(statement, ForStatement.class);
	}
	
	public static boolean isLabelledStatement(Statement statement) {
		return isOfType(statement, LabelledStatement.class);
	}

	private static boolean isOfType(Statement statement, Class<?> expectedClass) {
		Class<? extends Statement> clazz = statement.getClass();
		return expectedClass.equals(clazz);
	}
}
