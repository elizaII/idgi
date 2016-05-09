package com.idgi.core;

import java.util.List;

public abstract class QuizDecorator implements IQuiz {
    protected IQuiz decoratedQuiz;

    public QuizDecorator(IQuiz decoratedQuiz) {
        this.decoratedQuiz = decoratedQuiz;
    }

    @Override
    public int getID() {
        return this.decoratedQuiz.getID();
    }

    @Override
    public int length() {
        return this.decoratedQuiz.length();
    }

    @Override
    public void addQuestion(Question question) {
        this.decoratedQuiz.addQuestion(question);
    }

    @Override
    public void addQuestions(List<Question> questions) {
        this.decoratedQuiz.addQuestions(questions);
    }

    @Override
    public void nextQuestion() {
        this.decoratedQuiz.nextQuestion();
    }

    @Override
    public Question getCurrentQuestion() {
        return this.decoratedQuiz.getCurrentQuestion();
    }

    @Override
    public List<Question> getQuestions() {
        return this.decoratedQuiz.getQuestions();
    }

    @Override
    public boolean hasQuestion(Question question) {
        return this.decoratedQuiz.hasQuestion(question);
    }

    @Override
    public boolean isLastQuestion() {
        return this.decoratedQuiz.isLastQuestion();
    }

    @Override
    public boolean isFinished() {
        return this.decoratedQuiz.isFinished();
    }

    @Override
    public int getCorrectAnswerAmount() {
        return this.decoratedQuiz.getCorrectAnswerAmount();
    }

    /* Implement in subclasses?
    @Override
    public int getPointsEarned() {
        return 0;
    }*/
}
