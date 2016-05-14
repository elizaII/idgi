package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimedQuiz extends QuizDecorator {

    private int time;
    private int remainingTime;

    private String type = "timedQuiz";

    //Dummy constructor for Json because it can't provide arguments
    private TimedQuiz(){
        super();
    }


    //Since Json can't recognise parameters we have to manually set
    //the time after creating the TimedQuiz too
    //Todo... better solution for Json's unrecognising of properties
    /**
     * Creates a quiz with a timer
     * @param decoratedQuiz The normal quiz that doesn't have a set time
     * @param time The time for the quiz in milliseconds
     */
    public TimedQuiz(IQuiz decoratedQuiz, int time){
        super(decoratedQuiz);
        time = this.time;
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
        return (int) remainingTime/10;
    }

}
