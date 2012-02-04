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
package net.primitive.javascript.interpreter.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FastStack<T> {

	private Slot<T> topSlot;

	private static class Slot<T> {
		private Slot<T> next;
		private T item;
	}

	public T pop() {
		T topItem = topSlot.item;
		topSlot = topSlot.next;
		return topItem;
	}

	public void push(T object) {
		Slot<T> newSlot = new Slot<T>();
		newSlot.item = object;
		newSlot.next = topSlot;
		topSlot = newSlot;
	}

	public T peek() {
		return topSlot != null ? topSlot.item : null;
	}

	public boolean isEmpty() {
		return topSlot == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder stringBuilder = new ToStringBuilder(this,
				ToStringStyle.MULTI_LINE_STYLE);

		Slot<T> nextSlot = topSlot;
		while (nextSlot != null) {
			stringBuilder.append("item",nextSlot.item);
			nextSlot = nextSlot.next;
		}

		return stringBuilder.toString();
	}
}
