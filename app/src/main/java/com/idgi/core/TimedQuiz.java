package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.idgi.session.SessionData;

import java.util.List;

/*
Decorated Quiz with a set time for completion
 */
@JsonTypeName("timedQuiz")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimedQuiz extends QuizDecorator {

    private int time;
    private int remainingTime;

    private String name;

    private static final int TIME_PER_QUESTION = 5000;

    //Dummy constructor for JSON because it can't provide arguments
    private TimedQuiz(){
        super();
    }


    /** Default constructor that creates a timed quiz
     * with the time set to 5 seconds per question
     * decoratedQuiz is the normal quiz without a set time */
    public TimedQuiz(IQuiz decoratedQuiz) {
        super(decoratedQuiz);
        int amountOfQuestions = getQuestions().size();
        this.time = amountOfQuestions * TIME_PER_QUESTION;

    }

    /* Creates a quiz with a timer where the time
     * is counted in milliseconds */
    public TimedQuiz(IQuiz decoratedQuiz, int time){
        super(decoratedQuiz);
        this.time = time;
    }

    public static TimedQuiz from(IQuiz quiz) {
        if(quiz instanceof Quiz){
            return new TimedQuiz(quiz);
        }

        return (TimedQuiz) quiz;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPointsEarned() {
        int maximalScore = this.getCorrectAnswerAmount()*100;
        int total = super.getPointsEarned();

        if(maximalScore == total){
            total += calculateTimeBonus();
        }
        //Todo... better calculation because the questions are now not uniquely identified
        return total;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return this.time;
    }

    public void setRemainingTime(int remainingTime){
        this.remainingTime = remainingTime;
    }

    private int calculateTimeBonus(){
        return remainingTime/10;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NameableType getType() {
        return NameableType.QUIZ;
    }
}
