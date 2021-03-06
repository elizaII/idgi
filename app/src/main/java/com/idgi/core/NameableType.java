package com.idgi.core;

/*
Used to give each type of nameable a unique identifier
 */
public enum NameableType {
	SCHOOL(0),SUBJECT(1), COURSE(2), LESSON(3), QUIZ(4),
	HAT(5), USER(6), STUDENT(7), TEACHER(8);

	private int value;
	private static NameableType[] values = values();

	NameableType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static NameableType fromInteger(int x) {
		return values[x];
	}
}
