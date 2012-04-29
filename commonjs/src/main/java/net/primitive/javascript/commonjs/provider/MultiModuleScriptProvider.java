package net.primitive.javascript.commonjs.provider;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import net.primitive.javascript.commonjs.ModuleScript;
import net.primitive.javascript.commonjs.ModuleScriptProvider;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;

/**
 * A multiplexer for module script providers.
 * 
 * @author Attila Szegedi
 * @version $Id$
 */
public class MultiModuleScriptProvider implements ModuleScriptProvider {
	private final ModuleScriptProvider[] providers;

	/**
	 * Creates a new multiplexing module script provider tht gathers the
	 * specified providers
	 * 
	 * @param providers
	 *            the providers to multiplex.
	 */
	public MultiModuleScriptProvider(Iterable<? extends ModuleScriptProvider> providers) {
		final List<ModuleScriptProvider> l = new LinkedList<ModuleScriptProvider>();
		for (ModuleScriptProvider provider : providers) {
			l.add(provider);
		}
		this.providers = l.toArray(new ModuleScriptProvider[l.size()]);
	}

	public ModuleScript getModuleScript(Scope scope, String moduleId, URI uri, Scriptable paths) throws Exception {
		for (ModuleScriptProvider provider : providers) {
			final ModuleScript script = provider.getModuleScript(scope, moduleId, uri, paths);
			if (script != null) {
				return script;
			}
		}
		return null;
	}
}
