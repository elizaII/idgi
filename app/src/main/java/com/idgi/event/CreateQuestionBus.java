package com.idgi.event;

import com.idgi.core.Question;

/**
 * Communicates between creating questions-dialog and the create question-dialog
 */
public class CreateQuestionBus extends EventBus<CreateQuestionBus.Listener> {

    public void broadcastCreatedQuestion(Question question){
        for(Listener listener : getListeners()) {
            listener.onQuestionCreated(question);
        }
    }

    public interface Listener {
        void onQuestionCreated(Question question);
    }
}
