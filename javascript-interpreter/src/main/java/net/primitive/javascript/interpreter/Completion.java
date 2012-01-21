/**
 * Copyright (C) 2011 Primitive Team <jpalka@gmail.com>
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

package net.primitive.javascript.interpreter;

import net.primitive.javascript.core.ast.AstNode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Completion {

	private CompletionType type;

	private Object value;

	private AstNode target;

	protected Completion(CompletionType type, Object value, AstNode target) {
		super();
		this.type = type;
		this.value = value;
		this.target = target;
	}

	/**
	 * @return the type
	 */
	public CompletionType getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the target
	 */
	public AstNode getTarget() {
		return target;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
	
	public void setType(CompletionType type) {
		this.type = type;
	}

	public void setTarget(AstNode target) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder stringBuilder = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).append("type", type)
				.append("value", value).append("target", target);
		return stringBuilder.toString();
	}

}
