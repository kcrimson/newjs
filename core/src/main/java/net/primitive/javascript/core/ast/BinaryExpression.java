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

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.primitive.javascript.core.BinaryOperator;

@AllArgsConstructor
public final class BinaryExpression extends Expression {

	@Getter private final BinaryOperator operator;
	@Getter private final Expression op1;
	@Getter private final Expression op2;

	@Override
	public void accept(ExpressionVisitor visitor) {
		visitor.visitBinaryExpression(this);
	}
}
