package com.idgi.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Wrapping an object with an enum to identify the
 * reason of sending and receiving an object through
 * the bus.
 */
@JsonIgnoreProperties({"event", "data"})
public class BusEvent {

    private Event event;
    private Object data;

    public BusEvent(Event event, Object data){
        this.event = event;
        this.data = data;
    }

    public Event getEvent(){
        return event;
    }

    public Object getData(){
        return data;
    }
}
