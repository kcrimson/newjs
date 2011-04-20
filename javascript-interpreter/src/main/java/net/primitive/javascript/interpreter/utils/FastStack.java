package net.primitive.javascript.interpreter.utils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
