package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.visitors.SourceElementVisitor;

public class AbstractSourceElementVisitor implements SourceElementVisitor {

	private final Scriptable scope;

	protected AbstractSourceElementVisitor(Scriptable scope) {
		super();
		this.scope = scope;
	}

	@Override
	public Scriptable getScope() {
		return scope;
	}

}
