package net.primitive.javascript.core.natives;

import static net.primitive.javascript.core.Convertions.toNumber;
import net.primitive.javascript.core.Convertions;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.Undefined;

/**
 * 
 * Implementation of JavaScript Array prototype
 * 
 * @author jpalka@gmail.com
 */
public class JSArray extends ScriptableObject {

	private JSFunction _js_pop = new JSFunction() {

		@Override
		public Object call(Scope scope, Scriptable thisObj, Object[] args) {
			return pop(scope, thisObj, args);
		}

		@Override
		public Object[] bindParameters(Object[] actualParameters) {
			return null;
		}

		@Override
		public Scope getScope() {
			return null;
		}

	};
	private JSFunction _js_push = new JSFunction() {

		@Override
		public Scope getScope() {
			return null;
		}

		@Override
		public Object call(Scope scope, Scriptable thisObj, Object[] args) {
			return push(scope, thisObj, args);
		}

		@Override
		public Object[] bindParameters(Object[] actualParameters) {
			return null;
		}
	};;

	public JSArray() {

		put("pop", _js_pop);
		put("push", _js_push);
	}

	/**
	 * Array.protoype.pop()
	 * 
	 * @param scope
	 * @param thisObject
	 * @param objects
	 * @return
	 */
	public Object pop(Scope scope, Scriptable thisObj, Object[] args) {
		double len = toNumber(thisObj.get("length"));
		Object element = Undefined.Value;
		if (len > 0) {
			String indx = Convertions.toString(len - 1);
			element = thisObj.get(indx);
			thisObj.delete(indx, true);
			thisObj.put("length", indx);
		}
		return element;
	}

	public Object push(Scope scope, Scriptable thisObj, Object[] args) {
		// Let lenVal be the result of calling the [[Get]] internal method of O
		// with argument "length".
		Object lenVal = thisObj.get("length");
		// 3. Let n be ToUint32(lenVal).
		int n = Convertions.toUInt32(lenVal);
		// 4. Let items be an internal List whose elements are, in left to right
		// order, the arguments that were passed to this function invocation.
		Object[] items = args;
		for (Object e : items) {
			thisObj.put(Convertions.toString(n), e);
			n++;
			thisObj.put("length", n);
		}
		// 5. Repeat, while items is not empty
		// a. Remove the first element from items and let E be the value of the
		// element.
		// b. Call the [[Put]] internal method of O with arguments ToString(n),
		// E, and true.
		// c. Increase n by 1.
		// 6. Call the [[Put]] internal method of O with arguments "length", n,
		// and true.
		// 7. Return n.
		return n;
	}

}
