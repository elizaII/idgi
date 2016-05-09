package com.idgi.core;

import java.util.List;

public class TimedQuiz implements IQuiz {

    private Quiz quiz;
    private int time;
    private int remainingTime;

    public TimedQuiz(int time){
        quiz = new Quiz();
        time = this.time;
    }

    @Override
    public int getID() {
        return this.quiz.getID();
    }

    @Override
    public int length() {
        return this.quiz.length();
    }

    @Override
    public void addQuestion(Question question) {
        this.quiz.addQuestion(question);
    }

    @Override
    public void addQuestions(List<Question> questions) {
        this.quiz.addQuestions(questions);
    }

    @Override
    public void nextQuestion() {
        this.quiz.nextQuestion();
    }

    @Override
    public Question getCurrentQuestion() {
        return this.quiz.getCurrentQuestion();
    }

    @Override
    public List<Question> getQuestions() {
        return this.quiz.getQuestions();
    }

    @Override
    public boolean hasQuestion(Question question) {
        return this.quiz.hasQuestion(question);
    }

    @Override
    public boolean isLastQuestion() {
        return this.quiz.isLastQuestion();
    }

    @Override
    public boolean isFinished() {
        return this.quiz.isFinished();
    }

    @Override
    public int getCorrectAnswerAmount() {
        return this.quiz.getCorrectAnswerAmount();
    }

    @Override
    public int getPointsEarned() {
        int maximalScore = this.getCorrectAnswerAmount()*100;
        int total = this.quiz.getPointsEarned();

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
