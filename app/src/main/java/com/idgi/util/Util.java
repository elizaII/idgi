package com.idgi.util;

import com.idgi.core.Nameable;

import java.util.Comparator;

/*
Defines utility methods used in different parts of the application
 */
public class Util {

	public static final Comparator SORT_BY_NAME = new Comparator<Nameable>() {
		@Override
		public int compare(Nameable u1, Nameable u2) {
			return u1.getName().compareToIgnoreCase(u2.getName());
		}
	};
}
