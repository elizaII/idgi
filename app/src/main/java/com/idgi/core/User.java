package com.idgi.core;

import android.graphics.drawable.Drawable;

public class User {
    private String name;
    private String email;
    private String phoneNumber;

    private Statistics statistics;



    private Drawable image;

    public User(String name){
        this.name = name;
		this.statistics = new Statistics();
    }

    public User(String name, String email, Drawable image){
        this.name = name;
        this.email = email;
        this.image = image;
        this.statistics = new Statistics();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String eMail) {
        this.email = eMail;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public int getStat(Statistics.Property property) {
        return statistics.get(property);
    }

    public Drawable getImage() {
        return image;
    }

    public void givePointsForQuiz(int quizID, int points) {
        statistics.updateQuizPoints(quizID, points);
    }

    public int getPoints() {
        return statistics.get(Statistics.Property.POINTS);
    }
}
