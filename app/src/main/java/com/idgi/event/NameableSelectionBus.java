package com.idgi.event;

/**
 * Communicates between quiz dialog and lesson-activity to tell which quiz has been picked
 */
public class NameableSelectionBus extends EventBus<NameableSelectionBus.Listener>{

    public void broadcastSelection(String name) {
        for(Listener listener : getListeners()) {
            listener.onNameableSelected(name);
        }
    }

    public interface Listener {
        void onNameableSelected(String name);
    }
}
