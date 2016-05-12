package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Quiz.class, name = "quiz"),
        @JsonSubTypes.Type(value = TimedQuiz.class, name = "timedQuiz")
})
public interface IQuiz {

    String getID();

    int length();

    void addQuestion(Question question);

    void addQuestions(List<Question> questions);

    void nextQuestion();

    void reset();

    Question getCurrentQuestion();

    List<Question> getQuestions();

    boolean hasQuestion(Question question);

    boolean isLastQuestion();

    boolean isFinished();

    int getCorrectAnswerAmount();

    int getPointsEarned();

}
