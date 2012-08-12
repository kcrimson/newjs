package net.primitive.javascript.classgen;

/**
 * Dummy classloader used to load class from bytes array.
 * 
 * @author jpalka@gmail.com
 * 
 */
public class DefiningClassLoader extends ClassLoader {

	private final ClassLoader parentClassLoader;

	public DefiningClassLoader(ClassLoader parentClassLoader) {
		this.parentClassLoader = parentClassLoader;
	}

	public Class<?> defineClass(String name, byte[] data) {
		return super.defineClass(name, data, 0, data.length, null);
	}

	public void linkClass(Class<?> cl) {
		resolveClass(cl);
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> cl = findLoadedClass(name);
		if (cl == null) {
			if (parentClassLoader != null) {
				cl = parentClassLoader.loadClass(name);
			} else {
				cl = findSystemClass(name);
			}
		}
		if (resolve) {
			resolveClass(cl);
		}
		return cl;
	}

}
