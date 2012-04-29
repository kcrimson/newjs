package net.primitive.javascript.commonjs.provider;

import java.net.URLConnection;

/**
 * Interface for URL connection based security domain providers. Used by
 * {@link UrlModuleSourceProvider} to create Rhino security domain objects for
 * newly loaded scripts (see
 * {@link Context#compileReader(java.io.Reader, String, int, Object)}) based on
 * the properties obtainable through their URL connection.
 * 
 * @author Attila Szegedi
 * @version $Id$
 */
public interface UrlConnectionSecurityDomainProvider {
	/**
	 * Create a new security domain object for a script source identified by its
	 * URL connection.
	 * 
	 * @param urlConnection
	 *            the URL connection of the script source
	 * @return the security domain object for the script source. Can be null if
	 *         no security domain object can be created, although it is
	 *         advisable for the implementations to be able to create a security
	 *         domain object for any URL connection.
	 */
	public Object getSecurityDomain(URLConnection urlConnection);
}