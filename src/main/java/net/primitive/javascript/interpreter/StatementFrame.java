package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.ast.Statement;

public class StatementFrame {

	private Statement statement;

	private Object returnValue = Undefined.Value;

	private Object exceptionValue = Undefined.Value;

	public StatementFrame() {
		super();
	}

	/**
	 * @return the returnValue
	 */
	public Object getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue
	 *            the returnValue to set
	 */
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * @return the exceptionValue
	 */
	public Object getExceptionValue() {
		return exceptionValue;
	}

	/**
	 * @param exceptionValue
	 *            the exceptionValue to set
	 */
	public void setExceptionValue(Object exceptionValue) {
		this.exceptionValue = exceptionValue;
	}

	/**
	 * @param statement
	 *            the statement to set
	 */
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return statement;
	}

}
