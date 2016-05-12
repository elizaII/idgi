package com.idgi.core;

import java.util.List;

public interface IQuiz {

    String getID();

    int length();

    void addQuestion(Question question);

    void addQuestions(List<Question> questions);

    void nextQuestion();

    Question getCurrentQuestion();

    List<Question> getQuestions();

    boolean hasQuestion(Question question);

    boolean isLastQuestion();

    boolean isFinished();

    int getCorrectAnswerAmount();

    int getPointsEarned();

}
