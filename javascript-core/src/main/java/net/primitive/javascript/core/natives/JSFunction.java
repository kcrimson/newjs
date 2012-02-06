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
package net.primitive.javascript.core.natives;

import java.util.List;

import net.primitive.javascript.core.Callable;
import net.primitive.javascript.core.Constructor;
import net.primitive.javascript.core.Function;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.TypeErrorException;

public class JSFunction extends ScriptableObject implements Function, Constructor {

	@Override
	public Object call(Scope scope, Scriptable thisObj, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] bindParameters(Object[] actualParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scope getScope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scriptable construct(Scope scope, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object hasInstance(Object lvar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getParameterList() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object apply(Scriptable thisObj, Object args[]) {
		if (thisObj instanceof Callable) {
			((Callable) thisObj).call(null, thisObj, args);
		}

		throw new TypeErrorException();
	}

}
