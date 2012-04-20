package net.primitive.javascript.core.jdk;

import java.util.Enumeration;
import java.util.Map;

import net.primitive.javascript.core.BaseScriptableObject;
import net.primitive.javascript.core.PropertyDescriptor;

public class JavaPackageWrapper extends BaseScriptableObject {

	private final String packagePartName;
	private final JavaPackageWrapper parentPackageWrapper;

	public JavaPackageWrapper(String propertyName) {
		this.parentPackageWrapper = null;
		this.packagePartName = propertyName;
	}

	public JavaPackageWrapper(JavaPackageWrapper parentPackageWrapper,
			String propertyName) {
		this.parentPackageWrapper = parentPackageWrapper;
		this.packagePartName = propertyName;
	}

	@Override
	public PropertyDescriptor getOwnProperty(String propertyName) {
		// try to load class
		String classname = propertyName;
		JavaPackageWrapper parent = parentPackageWrapper;
		while (parent!= null) {
			classname = parent.getPackagePartName() + "."
					+ classname;
			parent = parent.getParentPackageWrapper();
		}
		
		System.out.println("loading: "+classname);

		try {
			JavaClassWrapper classWrapper = new JavaClassWrapper(
					Class.forName(classname));
			System.out.println("loaded: "+classname);

			PropertyDescriptor descriptor = new PropertyDescriptor(this)
					.isConfigurable(false).isEnumerable(false)
					.isWriteable(false);
			descriptor.setValue(classWrapper);
			return descriptor;
		} catch (ClassNotFoundException e) {
			System.out.println("package: "+propertyName);
			PropertyDescriptor descriptor = new PropertyDescriptor(this)
					.isConfigurable(false).isEnumerable(false)
					.isWriteable(false);
			descriptor.setValue(new JavaPackageWrapper(this, propertyName));
			return descriptor;
		}

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

	public String getPackagePartName() {
		return packagePartName;
	}

	public JavaPackageWrapper getParentPackageWrapper() {
		return parentPackageWrapper;
	}

}
