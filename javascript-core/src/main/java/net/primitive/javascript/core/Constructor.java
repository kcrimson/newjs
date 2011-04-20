package net.primitive.javascript.core;

public interface Constructor {
	/**
	 * Call the function as a constructor.
	 * 
	 * This method is invoked by the runtime in order to satisfy a use of the
	 * JavaScript <code>new</code> operator. This method is expected to create a
	 * new object and return it.
	 * 
	 * @param scope
	 *            an enclosing scope of the caller except when the function is
	 *            called from a closure.
	 * @param args
	 *            the array of arguments
	 * 
	 * @return the allocated object
	 */
	Scriptable construct(Scriptable scope, Object[] args);

}
