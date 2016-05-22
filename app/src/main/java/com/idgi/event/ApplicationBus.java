package com.idgi.event;

import com.google.common.eventbus.EventBus;
import com.idgi.Application;

import java.util.ArrayList;
import java.util.List;

/*
 * Delegates all events to this class to decouple classes
 */
public class ApplicationBus {

    private static EventBus bus = new EventBus();
    private static List<Object> listeners = new ArrayList<>();

    private ApplicationBus() {}

    public static void register(Object object) {
        bus.register(object);
        listeners.add(object);
    }

    public static void unregister(Object object) {
        bus.unregister(object);
        listeners.remove(object);
    }

    public static void post(Object object) {
        bus.post(object);
    }

    public static boolean hasListener(Object listener) {
        return listeners.contains(listener);
    }
}
