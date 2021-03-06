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
package net.primitive.javascript.core.ast;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public abstract class Statement extends AstNode {
	
	@Setter
	private List<String> labels;
	
	public abstract void accept(StatementVisitor visitor);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder stringBuilder = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
		return stringBuilder.toString();
	}
	
	public void addLabels( List<String> labels ){
		getLabels().addAll(labels);
	}
	
	public List<String> getLabels(){
		if( labels == null ){
			labels = new ArrayList<String>();
		}
		return labels;
	}
	
	public void addLabel( String label ){
		List<String> labels = getLabels();
		labels.add(label);
	}

}
