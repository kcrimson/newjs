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
package net.primitive.javascript.tests.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * list resources available from the classpath
 * 
 * @author stoughto
 */
public class ResourceList {
	/**
	 * for all elements of java.class.path get a Collection of resources Pattern
	 * pattern = Pattern.compile(".*"); gets all resources
	 * 
	 * @param pattern
	 *            the pattern to match
	 * @return the resources in the order they are found
	 */
	public static Collection<String> getResources(Pattern pattern) {
		ArrayList<String> retval = new ArrayList<String>();
		String classPath = System.getProperty("java.class.path", ".");
		String[] classPathElements = classPath.split(System
				.getProperty("path.separator"));
		for (String element : classPathElements) {
			retval.addAll(getResources(element, pattern));
		}
		return retval;
	}

	private static Collection<String> getResources(String element,
			Pattern pattern) {

		ArrayList<String> retval = new ArrayList<String>();
		File file = new File(element);
		if (file.isDirectory()) {
			retval.addAll(getResourcesFromDirectory(file, pattern));
		} else {
			// retval.addAll(getResourcesFromJarFile(file, pattern));
		}
		return retval;
	}

	//TODO file path from JARs is not absolute, fix it
	private static Collection<String> getResourcesFromJarFile(File file,
			Pattern pattern) {
		ArrayList<String> retval = new ArrayList<String>();
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		} catch (ZipException e) {
			throw new Error(e);
		} catch (IOException e) {
			throw new Error(e);
		}
		Enumeration<?> e = zf.entries();
		while (e.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			String fileName = ze.getName();
			boolean accept = pattern.matcher(fileName).matches();
			if (accept) {
				retval.add(fileName);
			}
		}
		try {
			zf.close();
		} catch (IOException e1) {
			throw new Error(e1);
		}
		return retval;
	}

	private static Collection<String> getResourcesFromDirectory(File directory,
			Pattern pattern) {
		ArrayList<String> retval = new ArrayList<String>();
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				try {
					String fileName = file.getCanonicalPath();
					boolean accept = pattern.matcher(fileName).matches();
					if (accept) {
						retval.add(fileName);
					}
				} catch (IOException e) {
					throw new Error(e);
				}
			}
		}
		return retval;
	}

	/**
	 * list the resources that match args[0]
	 * 
	 * @param args
	 *            args[0] is the pattern to match, or list all resources if
	 *            there are no args
	 */
	public static void main(String[] args) {
		Pattern pattern;
		if (args.length < 1) {
			pattern = Pattern.compile(".*");
		} else {
			pattern = Pattern.compile(args[0]);
		}
		Collection<String> list = ResourceList.getResources(pattern);
		for (String name : list) {
			System.out.println(name);
		}

	}
}
