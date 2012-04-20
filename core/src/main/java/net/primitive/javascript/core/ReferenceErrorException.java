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
package net.primitive.javascript.core;

public class ReferenceErrorException extends EcmaScriptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1975888452078284271L;

	public ReferenceErrorException(Reference ref) {
		super(String.format("unable to resolve reference to '%s'",
				ref.getReferencedName()));
	}

	public ReferenceErrorException(String msg) {
		super(msg);
	}
}