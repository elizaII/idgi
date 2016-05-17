package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("timedQuiz")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimedQuiz extends QuizDecorator {

    private int time;
    private int remainingTime;

    private static final int TIME_PER_QUESTION = 5000;

    //Dummy constructor for JSON because it can't provide arguments
    private TimedQuiz(){
        super();
    }


    /**
     * Creates a timed quiz with the time set to 5 seconds per question
     * @param decoratedQuiz The normal quiz without a set time
     */
    public TimedQuiz(IQuiz decoratedQuiz) {
        super(decoratedQuiz);
        int amountOfQuestions = getQuestions().size();
        this.time = amountOfQuestions * TIME_PER_QUESTION;
    }

    /**
     * Creates a quiz with a timer
     * @param decoratedQuiz The normal quiz that doesn't have a set time
     * @param time The time for the quiz in milliseconds
     */
    public TimedQuiz(IQuiz decoratedQuiz, int time){
        super(decoratedQuiz);
        this.time = time;
    }

    public static TimedQuiz create(IQuiz quiz) {
        if(quiz instanceof Quiz){
            return new TimedQuiz(quiz);
        }

        return (TimedQuiz) quiz;
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

}
