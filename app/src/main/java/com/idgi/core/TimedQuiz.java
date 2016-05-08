package com.idgi.core;

import java.util.List;

public class TimedQuiz implements IQuiz {

    private Quiz quiz;
    private int time;

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
        //Todo...
        return 0;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return this.time;
    }
}
