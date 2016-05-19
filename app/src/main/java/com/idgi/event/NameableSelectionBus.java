package com.idgi.event;


import com.idgi.core.Nameable;

/**
 * Communicates between quiz dialog and lesson-activity to tell which quiz has been picked
 */
public class NameableSelectionBus extends EventBus<NameableSelectionBus.Listener>{

    public void broadcastSelection(Nameable nameable) {
        for(Listener listener : getListeners()) {
            listener.onNameableSelected(nameable);
        }
    }

    public interface Listener {
        void onNameableSelected(Nameable nameable);
    }
}
