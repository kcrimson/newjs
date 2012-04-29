package net.primitive.javascript.commonjs;

import java.net.URI;

import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;

/**
 * A top-level module scope. This class provides methods to retrieve the
 * module's source and base URIs in order to resolve relative module IDs and
 * check sandbox constraints.
 */
public class ModuleScope extends ScriptableObject {

	private final URI uri;
	private final URI base;

	public ModuleScope(Scriptable prototype, URI uri, URI base) {
		this.uri = uri;
		this.base = base;
		setPrototype(prototype);
	}

	public URI getUri() {
		return uri;
	}

	public URI getBase() {
		return base;
	}
}
