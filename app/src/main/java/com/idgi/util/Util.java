package com.idgi.util;

import java.util.List;

public class Util {

	public static <T extends Nameable> T findByName(List<T> list, String name) {
		for (T element : list)
			if (namePredicate.accepts(element, name))
				return element;

		return null;
	}

	public static <T extends Nameable> boolean listContains(List<T> list, T nameable) {
		for (T element : list)
			if (nameable.equals(element))
				return true;

		return false;
	}

	private static final Predicate<Nameable> namePredicate = new Predicate<Nameable>() {

		public boolean accepts(Nameable namedObject, Object... extras) {
			if (extras.length < 1)
				throw new IllegalArgumentException("Named predicate extras must contain the name as a String at index [0].");

			String name = extras[0].toString();
			return namedObject.getName().equals(name);
		}
	};
}
