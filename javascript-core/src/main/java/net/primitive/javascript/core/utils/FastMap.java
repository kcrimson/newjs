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
package net.primitive.javascript.core.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FastMap<K, V> implements Map<K, V> {

	private class Node<K, V> {

		final int hash;

		protected final Object keys;

		protected final Object values;

		protected Node<K, V> left;

		protected Node<K, V> right;

		private Node(K key, V value, int keyHashCode) {
			super();
			this.hash = keyHashCode;
			this.keys = key;
			this.values = value;
		}

	}

	private Node<K, V> root;
	private int maxKeyHash;
	private int minKeyHash;

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object arg0) {
		int keyHashCode = arg0.hashCode();
		if (root != null
				&& (keyHashCode < minKeyHash || keyHashCode > maxKeyHash)) {
			return false;
		}

		Node<K, V> node = root;

		for (;;) {
			for (;;) {
				if (node == null) {
					return false;
				}

				if (node.hash == keyHashCode) {
					return true;
				}

				if (keyHashCode > node.hash) {
					node = node.right;
				} else {
					node = node.left;
				}

			}

		}

	}

	@Override
	public boolean containsValue(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V get(Object key) {

		Node<K, V> node = root;
		int keyHash = key.hashCode();

		for (;;) {
			if (node == null) {
				return null;
			}

			if (node.hash == keyHash && key.equals(node.keys)) {
				return (V) node.values;
			}

			if (keyHash > node.hash) {
				node = node.right;
			} else {
				node = node.left;
			}

		}

	}

	@Override
	public boolean isEmpty() {
		return root != null;
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V put(K key, V value) {

		Node<K, V> node = root;
		int keyHashCode = key.hashCode();

		for (;;) {

			if (node == null) {
				root = new Node<K, V>(key, value, keyHashCode);
				maxKeyHash = keyHashCode;
				minKeyHash = keyHashCode;
				return null;
			}

			if (node.hash == keyHashCode) {
				// match key by equals with value
			}

			if (keyHashCode > node.hash) {
				// right look at
				if (node.right == null) {
					node.right = new Node<K, V>(key, value, keyHashCode);
					maxKeyHash = keyHashCode;
					return null;// exit
				}
				node = node.right;
			} else {
				if (node.left == null) {
					node.left = new Node<K, V>(key, value, keyHashCode);
					minKeyHash = keyHashCode;
					return null;
				}
				node = node.left;
			}
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V remove(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] argv) {

		long timeMillis = System.currentTimeMillis();
		Map<String, String> map;
		int rep = 1000000;
		for (int j = 0; j < rep; j++) {
			map = new FastMap<String, String>();
			map.put("01", "zero");
			map.put("11", "one");
			map.put("21", "two");
			map.put("31", "three");
			map.put("41", "four");
			map.put("51", "five");
			map.put("61", "six");
			map.put("71", "seven");
			map.put("81", "eight");
			map.put("91", "nine");
			map.put("0", "zero");
			map.put("1", "one");
			map.put("2", "two");
			map.put("3", "three");
			map.put("4", "four");
			map.put("5", "five");
			map.put("6", "six");
			map.put("7", "seven");
			map.put("8", "eight");
			map.put("9", "nine");
			map.put("10", "ten");
			map.put("101", "ten");
			for (int i = 0; i < rep; i++) {
				map.containsKey("10");
			}
		}
		System.out.println(System.currentTimeMillis() - timeMillis);
		// System.gc();
		timeMillis = System.currentTimeMillis();
		for (int j = 0; j < rep; j++) {
			map = new HashMap<String, String>(16, 0.5f);
			map.put("01", "zero");
			map.put("11", "one");
			map.put("21", "two");
			map.put("31", "three");
			map.put("41", "four");
			map.put("51", "five");
			map.put("61", "six");
			map.put("71", "seven");
			map.put("81", "eight");
			map.put("91", "nine");
			map.put("0", "zero");
			map.put("1", "one");
			map.put("2", "two");
			map.put("3", "three");
			map.put("4", "four");
			map.put("5", "five");
			map.put("6", "six");
			map.put("7", "seven");
			map.put("8", "eight");
			map.put("9", "nine");
			map.put("10", "ten");
			map.put("101", "ten");
			for (int i = 0; i < rep; i++) {
				map.containsKey("10");
			}
		}
		System.out.println(System.currentTimeMillis() - timeMillis);
	}
}
