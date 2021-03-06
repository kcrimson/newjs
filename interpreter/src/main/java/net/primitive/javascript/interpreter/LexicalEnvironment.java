/**
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.primitive.javascript.interpreter;

import static net.primitive.javascript.interpreter.RuntimeContext.currentContext;
import net.primitive.javascript.core.Reference;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.ScopeBindings;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.Undefined;
import net.primitive.javascript.core.natives.StandardObjects;

public final class LexicalEnvironment {

	private LexicalEnvironment() {
	}

	/**
	 * Special case for creating object environment (used only for creating top
	 * level environment).
	 * 
	 * @param stdObjects
	 * @param bindingObject
	 * @param outerScope
	 * @return
	 */
	public static Scope newObjectEnvironment(StandardObjects stdObjects, Scriptable bindingObject) {
		return new Scope(stdObjects, null, new ObjectEnvironmentRecords(bindingObject, true));
	}

	public static Scope newObjectEnvironment(Scriptable bindingObject, Scope outerScope) {
		return new Scope(currentContext().getStandardObjects(), outerScope, new ObjectEnvironmentRecords(bindingObject, true));
	}

	public static Scope newDeclarativeEnvironment(Scope outerScope) {
		return new Scope(currentContext().getStandardObjects(), outerScope, new DeclarativeEnvironmentRecords());
	}

	public static Reference getIdentifierReference(Scope env, String name) {
		if (env == null) {
			return new ObjectReference(Undefined.Value, name);
		}

		ScopeBindings records = env.getBindings();
		if (records.hasBinding(name)) {
			return records.getBinding(name);
		}

		Scope outerenv = env.getOuterScope();
		return getIdentifierReference(outerenv, name);
	}
}
