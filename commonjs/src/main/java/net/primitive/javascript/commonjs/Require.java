package net.primitive.javascript.commonjs;

import static net.primitive.javascript.core.Reference.getValue;
import static net.primitive.javascript.core.jdk.JavaHost.unwrap;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Script;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.jdk.JavaHost;
import net.primitive.javascript.core.natives.JSFunction;
import net.primitive.javascript.core.natives.StandardObjects;

/**
 * Implements the require() function as defined by <a
 * href="http://wiki.commonjs.org/wiki/Modules/1.1">Common JS modules</a>. <h1>
 * Thread safety</h1> You will ordinarily create one instance of require() for
 * every top-level scope. This ordinarily means one instance per program
 * execution, except if you use shared top-level scopes and installing most
 * objects into them. Module loading is thread safe, so using a single require()
 * in a shared top-level scope is also safe. <h1>Creation</h1> If you need to
 * create many otherwise identical require() functions for different scopes, you
 * might want to use {@link RequireBuilder} for convenience. <h1>Making it
 * available</h1> In order to make the require() function available to your
 * JavaScript program, you need to invoke either {@link #install(Scriptable)} or
 * {@link #requireMain(Context, String)}.
 * 
 * @author Attila Szegedi
 * @version $Id$
 */
public class Require extends JSFunction {

	private final ModuleScriptProvider moduleScriptProvider;
	private final Scriptable nativeScope;
	private final Scriptable paths;
	private final boolean sandboxed;
	private final Script preExec;
	private final Script postExec;
	private String mainModuleId = null;
	private Scriptable mainExports;

	// Modules that completed loading; visible to all threads
	private final Map<String, Scriptable> exportedModuleInterfaces = new ConcurrentHashMap<String, Scriptable>();
	private final Object loadLock = new Object();
	// Modules currently being loaded on the thread. Used to resolve circular
	// dependencies while loading.
	private static final ThreadLocal<Map<String, Scriptable>> loadingModuleInterfaces = new ThreadLocal<Map<String, Scriptable>>();

	/**
	 * Creates a new instance of the require() function. Upon constructing it,
	 * you will either want to install it in the global (or some other) scope
	 * using {@link #install(Scriptable)}, or alternatively, you can load the
	 * program's main module using {@link #requireMain(Context, String)} and
	 * then act on the main module's exports.
	 * 
	 * @param cx
	 *            the current context
	 * @param nativeScope
	 *            a scope that provides the standard native JavaScript objects.
	 * @param moduleScriptProvider
	 *            a provider for module scripts
	 * @param preExec
	 *            an optional script that is executed in every module's scope
	 *            before its module script is run.
	 * @param postExec
	 *            an optional script that is executed in every module's scope
	 *            after its module script is run.
	 * @param sandboxed
	 *            if set to true, the require function will be sandboxed. This
	 *            means that it doesn't have the "paths" property, and also that
	 *            the modules it loads don't export the "module.uri" property.
	 */
	public Require(StandardObjects cx, Scriptable nativeScope, ModuleScriptProvider moduleScriptProvider, Script preExec, Script postExec, boolean sandboxed) {
		this.moduleScriptProvider = moduleScriptProvider;
		this.nativeScope = nativeScope;
		this.sandboxed = sandboxed;
		this.preExec = preExec;
		this.postExec = postExec;
		setPrototype(cx.getFunctionPrototype());
		if (!sandboxed) {
			paths = cx.newArray();
			PropertyDescriptor desc = new PropertyDescriptor(this).isConfigurable(false).isEnumerable(true).isWriteable(false);
			desc.setValue(paths);
			defineOwnProperty("paths", desc, true);
		} else {
			paths = null;
		}
	}

	/**
	 * Calling this method establishes a module as being the main module of the
	 * program to which this require() instance belongs. The module will be
	 * loaded as if require()'d and its "module" property will be set as the
	 * "main" property of this require() instance. You have to call this method
	 * before the module has been loaded (that is, the call to this method must
	 * be the first to require the module and thus trigger its loading). Note
	 * that the main module will execute in its own scope and not in the global
	 * scope. Since all other modules see the global scope, executing the main
	 * module in the global scope would open it for tampering by other modules.
	 * 
	 * @param cx
	 *            the current context
	 * @param mainModuleId
	 *            the ID of the main module
	 * @return the "exports" property of the main module
	 * @throws IllegalStateException
	 *             if the main module is already loaded when required, or if
	 *             this require() instance already has a different main module
	 *             set.
	 */
	public Scriptable requireMain(StandardObjects cx, String mainModuleId) {
		if (this.mainModuleId != null) {
			if (!this.mainModuleId.equals(mainModuleId)) {
				throw new IllegalStateException("Main module already set to " + this.mainModuleId);
			}
			return mainExports;
		}

		ModuleScript moduleScript;
		try {
			// try to get the module script to see if it is on the module path
			moduleScript = moduleScriptProvider.getModuleScript(cx, mainModuleId, null, paths);
		} catch (RuntimeException x) {
			throw x;
		} catch (Exception x) {
			throw new RuntimeException(x);
		}

		if (moduleScript != null) {
			mainExports = getExportedModuleInterface(cx, mainModuleId, null, true);
		} else if (!sandboxed) {

			URI mainUri = null;

			// try to resolve to an absolute URI or file path
			try {
				mainUri = new URI(mainModuleId);
			} catch (URISyntaxException usx) {
				// fall through
			}

			// if not an absolute uri resolve to a file path
			if (mainUri == null || !mainUri.isAbsolute()) {
				File file = new File(mainModuleId);
				if (!file.isFile()) {
					// throw ScriptRuntime.throwError(cx, nativeScope,
					// "Module \"" + mainModuleId + "\" not found.");
				}
				mainUri = file.toURI();
			}
			mainExports = getExportedModuleInterface(cx, mainUri.toString(), mainUri, true);
		}

		this.mainModuleId = mainModuleId;
		return mainExports;
	}

	/**
	 * Binds this instance of require() into the specified scope under the
	 * property name "require".
	 * 
	 * @param scope
	 *            the scope where the require() function is to be installed.
	 */
	public void install(Scriptable scope) {
		// ScriptableObject.putProperty(scope, "require", this);
	}

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {
		if (args == null || args.length < 1) {
			// throw ScriptRuntime.throwError(cx, scope,
			// "require() needs one argument");
		}

		String id = (String) unwrap(getValue(args[0]));
		URI uri = null;
		if (id.startsWith("./") || id.startsWith("../")) {
			if (!(thisObj instanceof ModuleScope)) {
				// throw ScriptRuntime.throwError(cx, scope,
				// "Can't resolve relative module ID \"" + id +
				// "\" when require() is used outside of a module");
			}

			ModuleScope moduleScope = (ModuleScope) thisObj;
			URI base = moduleScope.getBase();
			URI current = moduleScope.getUri();

			if (base == null) {
				// calling module is absolute, resolve to absolute URI
				// (but without file extension)
				uri = current.resolve(id);
				id = uri.toString();
			} else {
				// try to convert to a relative URI rooted on base
				id = base.relativize(current).resolve(id).toString();
				if (id.charAt(0) == '.') {
					// resulting URI is not contained in base,
					// throw error or make absolute depending on sandbox flag.
					if (sandboxed) {
						// throw ScriptRuntime.throwError(cx, scope, "Module \""
						// + id + "\" is not contained in sandbox.");
					} else {
						uri = current.resolve(id);
						id = uri.toString();
					}
				}
			}
		}
		//TODO Scope needs to delegate 
		return null;//getExportedModuleInterface(cx, id, uri, false);
	}

	@Override
	public Scriptable construct(Scope scope, Object[] args) {
		// throw ScriptRuntime.throwError(cx, scope,
		// "require() can not be invoked as a constructor");
		return null;
	}

	private Scriptable getExportedModuleInterface(StandardObjects cx, String id, URI uri, boolean isMain) {
		// Check if the requested module is already completely loaded
		Scriptable exports = exportedModuleInterfaces.get(id);
		if (exports != null) {
			if (isMain) {
				throw new IllegalStateException("Attempt to set main module after it was loaded");
			}
			return exports;
		}
		// Check if it is currently being loaded on the current thread
		// (supporting circular dependencies).
		Map<String, Scriptable> threadLoadingModules = loadingModuleInterfaces.get();
		if (threadLoadingModules != null) {
			exports = threadLoadingModules.get(id);
			if (exports != null) {
				return exports;
			}
		}
		// The requested module is neither already loaded, nor is it being
		// loaded on the current thread. End of fast path. We must synchronize
		// now, as we have to guarantee that at most one thread can load
		// modules at any one time. Otherwise, two threads could end up
		// attempting to load two circularly dependent modules in opposite
		// order, which would lead to either unacceptable non-determinism or
		// deadlock, depending on whether we underprotected or overprotected it
		// with locks.
		synchronized (loadLock) {
			// Recheck if it is already loaded - other thread might've
			// completed loading it just as we entered the synchronized block.
			exports = exportedModuleInterfaces.get(id);
			if (exports != null) {
				return exports;
			}
			// Nope, still not loaded; we're loading it then.
			final ModuleScript moduleScript = getModule(cx, id, uri);
			if (sandboxed && !moduleScript.isSandboxed()) {
				// throw ScriptRuntime.throwError(cx, nativeScope, "Module \"" +
				// id + "\" is not contained in sandbox.");
			}
			exports = cx.newObject();
			// Are we the outermost locked invocation on this thread?
			final boolean outermostLocked = threadLoadingModules == null;
			if (outermostLocked) {
				threadLoadingModules = new HashMap<String, Scriptable>();
				loadingModuleInterfaces.set(threadLoadingModules);
			}
			// Must make the module exports available immediately on the
			// current thread, to satisfy the CommonJS Modules/1.1 requirement
			// that "If there is a dependency cycle, the foreign module may not
			// have finished executing at the time it is required by one of its
			// transitive dependencies; in this case, the object returned by
			// "require" must contain at least the exports that the foreign
			// module has prepared before the call to require that led to the
			// current module's execution."
			threadLoadingModules.put(id, exports);
			try {
				// Support non-standard Node.js feature to allow modules to
				// replace the exports object by setting module.exports.
				Scriptable newExports = executeModuleScript(cx, id, exports, moduleScript, isMain);
				if (exports != newExports) {
					threadLoadingModules.put(id, newExports);
					exports = newExports;
				}
			} catch (RuntimeException e) {
				// Throw loaded module away if there was an exception
				threadLoadingModules.remove(id);
				throw e;
			} finally {
				if (outermostLocked) {
					// Make loaded modules visible to other threads only after
					// the topmost triggering load has completed. This strategy
					// (compared to the one where we'd make each module
					// globally available as soon as it loads) prevents other
					// threads from observing a partially loaded circular
					// dependency of a module that completed loading.
					exportedModuleInterfaces.putAll(threadLoadingModules);
					loadingModuleInterfaces.set(null);
				}
			}
		}
		return exports;
	}

	private Scriptable executeModuleScript(StandardObjects cx, String id, Scriptable exports, ModuleScript moduleScript, boolean isMain) {
		final Scriptable moduleObject = cx.newObject();
		URI uri = moduleScript.getUri();
		URI base = moduleScript.getBase();

		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(moduleObject).isConfigurable(false).isWriteable(false).isEnumerable(true);
		propertyDescriptor.setValue(id);

		moduleObject.defineOwnProperty("id", propertyDescriptor, false);

		// defineReadOnlyProperty(moduleObject, "id", id);

		if (!sandboxed) {
			propertyDescriptor = new PropertyDescriptor(moduleObject).isConfigurable(false).isWriteable(false).isEnumerable(true);
			propertyDescriptor.setValue(uri.toString());

			moduleObject.defineOwnProperty("uri", propertyDescriptor, false);
			// defineReadOnlyProperty(moduleObject, "uri", uri.toString());
		}
		final Scriptable executionScope = new ModuleScope(nativeScope, uri, base);
		// Set this so it can access the global JS environment objects.
		// This means we're currently using the "MGN" approach (ModuleScript
		// with Global Natives) as specified here:
		// <http://wiki.commonjs.org/wiki/Modules/ProposalForNativeExtension>
		executionScope.put("exports", exports);
		executionScope.put("module", moduleObject);
		moduleObject.put("exports", exports);
		install(executionScope);
		if (isMain) {
			// defineReadOnlyProperty(this, "main", moduleObject);
		}
		executeOptionalScript(preExec, cx, executionScope);
		moduleScript.getScript().execute(executionScope);
		executeOptionalScript(postExec, cx, executionScope);
		return Convertions.toObject(moduleObject.get("exports"));
	}

	private static void executeOptionalScript(Script script, StandardObjects cx, Scriptable executionScope) {
		if (script != null) {
			script.execute(executionScope);
		}
	}

	// private static void defineReadOnlyProperty(ScriptableObject obj, String
	// name, Object value) {
	// ScriptableObject.putProperty(obj, name, value);
	// obj.setAttributes(name, ScriptableObject.READONLY |
	// ScriptableObject.PERMANENT);
	// }

	private ModuleScript getModule(StandardObjects cx, String id, URI uri) {
		try {
			final ModuleScript moduleScript = moduleScriptProvider.getModuleScript(cx, id, uri, paths);
			if (moduleScript == null) {
				// throw ScriptRuntime.throwError(cx, nativeScope, "Module \"" +
				// id + "\" not found.");
			}
			return moduleScript;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// throw Context.throwAsScriptRuntimeEx(e);
			return null;
		}
	}

}