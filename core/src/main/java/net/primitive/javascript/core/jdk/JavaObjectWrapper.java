package net.primitive.javascript.core.jdk;

import java.util.Enumeration;
import java.util.Map;

import net.primitive.javascript.core.BaseScriptableObject;
import net.primitive.javascript.core.PropertyDescriptor;

/**
 * 
 * @author jpalka@gmail.com
 * 
 */
public class JavaObjectWrapper extends BaseScriptableObject {

	private final Object javaObject;

	public JavaObjectWrapper(Object javaObject) {
		this.javaObject = javaObject;
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {

		JavaMethodWrapper methodWrapper = new JavaMethodWrapper(javaObject,propertyName);
		
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(this);
		propertyDescriptor.setValue(methodWrapper);
		
		return propertyDescriptor;
	}

	@Override
	public boolean defineOwnProperty(String propertyName,
			PropertyDescriptor propertyDescriptor, boolean failureHandling) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String propertyName, boolean failureHandling) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Enumeration<String> enumeration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, PropertyDescriptor> getOwnProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getJavaObject() {
		return javaObject;
	}

}