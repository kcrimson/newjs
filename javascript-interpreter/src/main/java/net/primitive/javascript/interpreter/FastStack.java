package net.primitive.javascript.interpreter;

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

}
