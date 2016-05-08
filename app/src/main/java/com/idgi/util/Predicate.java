package com.idgi.util;

public interface Predicate<T> {
	boolean accepts(T object, Object... extras);
}
