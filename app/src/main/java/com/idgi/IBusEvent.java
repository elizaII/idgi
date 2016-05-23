package com.idgi;

public interface IBusEvent {

	enum Event {
		POINTS_UPDATED
	}

	Event getEvent();
	Object getData();
}
