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
			return pop(thisObj, args);
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
			return push(thisObj, args);
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
	 * Array.prototype.pop()
	 * 
	 * @param thisObject
	 * @param objects
	 * @return
	 */
	public static Object pop(Scriptable thisObj, Object[] args) {
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

	/**
	 * Array.prototype.push()
	 * 
	 * @param thisObject
	 * @param objects
	 * @return
	 */
	public static Object push(Scriptable thisObj, Object[] args) {
		Object lenVal = thisObj.get("length");
		int n = Convertions.toUInt32(lenVal);
		Object[] items = args;
		for (Object e : items) {
			thisObj.put(Convertions.toString(n), e);
			n++;
			thisObj.put("length", n);
		}
		return n;
	}

}
