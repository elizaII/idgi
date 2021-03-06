package com.idgi.core;

import java.util.List;

/*
Utility class used for finding objects in the model easily
 */
public final class ModelUtility {

	private ModelUtility() throws InstantiationException {
		throw new InstantiationException("This class should not be instantiated.");
	}

	public static <T extends Nameable> T findByName(List<T> list, String name) {
		for (T element : list)
		if (element.getName().equals(name))
				return element;

		return null;
	}
}
