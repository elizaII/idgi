package com.idgi.core;

import java.util.List;

public class TimedQuiz extends QuizDecorator {

    private Quiz quiz;
    private int time;
    private int remainingTime;

    public TimedQuiz(IQuiz decoratedQuiz, int time){
        super(decoratedQuiz);
        time = this.time;
    }

    @Override
    public int getPointsEarned() {
        int maximalScore = this.getCorrectAnswerAmount()*100;
        int total = decoratedQuiz.getPointsEarned();

        if(maximalScore == total){
            total += calculateTimeBonus(this.remainingTime);
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

    private int calculateTimeBonus(final int remainingTime){
        int difference = getTime() - remainingTime;

        return (int) difference/10;
    }

}
