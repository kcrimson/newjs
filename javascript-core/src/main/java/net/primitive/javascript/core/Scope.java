package net.primitive.javascript.core;

/**
 * Implementation of Lexical environment concept
 * 
 * @author http://bitbucket.org/kcrimson
 * 
 */
public class Scope {

	private final Scope outerScope;

	private final ScopeBindings bindings;

	public Scope(Scope outerScope, ScopeBindings bindings) {
		super();
		this.outerScope = outerScope;
		this.bindings = bindings;
	}

	/**
	 * @return the outerLexicalEnvironment
	 */
	public Scope getOuterScope() {
		return outerScope;
	}

	/**
	 * @return the environmentRecords
	 */
	public ScopeBindings getBindings() {
		return bindings;
	}
}
