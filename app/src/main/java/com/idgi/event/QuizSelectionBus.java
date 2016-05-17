package com.idgi.event;

/**
 * Communicates between quiz dialog and lesson-activity to tell which quiz has been picked
 */
public class QuizSelectionBus extends EventBus<QuizSelectionBus.Listener>{

    public void broadcastQuizSelection(QuizType quizType) {
        for(Listener listener : getListeners()) {
            listener.onQuizTypeSelected(quizType);
        }
    }

    public enum QuizType {
        NORMAL, TIMED
    }

    public interface Listener {
        void onQuizTypeSelected(QuizType quizType);
    }
}
