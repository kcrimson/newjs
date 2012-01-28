package net.primitive.javascript.core.natives;

import static com.google.common.collect.Maps.transformValues;
import static net.primitive.javascript.core.Convertions.toObject;
import static net.primitive.javascript.core.Reference.getValue;

import java.util.Map;

import net.primitive.javascript.core.Operators;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.Scope;
import net.primitive.javascript.core.Scriptable;
import net.primitive.javascript.core.ScriptableObject;
import net.primitive.javascript.core.TypeErrorException;
import net.primitive.javascript.core.Types;

import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Function;

public class JSON extends ScriptableObject {

	public JSON() {
		super("JSON");
	}

	public static Object stringify(Scope scope, Object[] args) {
		Scriptable object = toObject(extractArgument(args));
		Map<String, PropertyDescriptor> ownProperties = object
				.getOwnProperties();

		Map<String, Object> map = transformValues(ownProperties,
				new Function<PropertyDescriptor, Object>() {

					@Override
					public Object apply(PropertyDescriptor arg0) {
						return arg0.getValue();
					}
				});

		return new JSONObject(map).toString();
	}

	/**
	 * Returns first argument or null if no arguments at all
	 * 
	 * @param args
	 * @param value
	 * @return
	 * @throws TypeErrorException
	 *             - when first argument is not {@link Scriptable}
	 */
	private static Object extractArgument(Object[] args) {
		Object firstArg = null;
		if (args != null && args.length > 0) {
			firstArg = getValue(args[0]);
		}

		if (Types.Object.equals(Operators.TypeOf.operator(firstArg))) {
			return firstArg;
		}

		throw new TypeErrorException();
	}

}
