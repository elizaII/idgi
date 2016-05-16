package com.idgi.event;

import java.util.List;

/**
 * Allows for decoupled communication between classes
 */
public abstract class EventBus<T> {

    private List<T> listeners;

    public void addListener(T listener) {
        listeners.add(listener);
    }

    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    protected List<T> getListeners() {
        return listeners;
    }
}
