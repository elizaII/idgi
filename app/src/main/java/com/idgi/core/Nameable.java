package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Nameable {

	String getName();

	@JsonIgnore
	NameableType getType();
}
