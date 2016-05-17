package com.idgi.event;

import com.idgi.core.IQuiz;

/**
 * Communicates between create quiz dialog and create lesson activity
 */
public class CreateQuizBus extends EventBus<CreateQuizBus.Listener>{

    public void broadcastQuizCreated(IQuiz quiz) {
        for(Listener listener : getListeners()) {
            listener.onQuizCreated(quiz);
        }
    }

    public interface Listener {
        void onQuizCreated(IQuiz quiz);
    }
}
