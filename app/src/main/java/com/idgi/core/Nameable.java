package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Nameable {
	enum Type {
		SCHOOL(0), SUBJECT(1), COURSE(2), LESSON(3), QUIZ(4), HAT(5);

		private int value;
		private static Type[] values = values();

		Type(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Type fromInteger(int x) {
			return values[x];
		}
	}

	String getName();

	@JsonIgnore
	Type getType();
}
