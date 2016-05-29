package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
Common interface for Model-classes that can be created by users.
 */
public interface Nameable {

	String getName();

	@JsonIgnore
	NameableType getType();
}
