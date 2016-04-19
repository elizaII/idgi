package com.idgi.util;

public class Proxy<T> {
	
	private T object;

	public Proxy(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
}
