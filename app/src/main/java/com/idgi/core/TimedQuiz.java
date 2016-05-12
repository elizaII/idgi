package com.idgi.core;

import java.util.List;

public class TimedQuiz extends QuizDecorator {

    private Quiz quiz;
    private int time;
    private int remainingTime;

    private String type = "timedQuiz";

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
