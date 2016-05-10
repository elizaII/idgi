package com.idgi.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String name;
    private String email;

    private String phoneNumber;
    private String age;
    private Drawable image;

    private Statistics statistics;

    private User() {}

	public User(String name){
		this.name = name;
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
    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }
    public String getAge() {
        return this.age;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

	@JsonIgnore
    public int getStat(Statistics.Property property) {
        return statistics.get(property);
    }

    public Drawable getImage() {
        return image;
    }

    @JsonIgnore
    public int getPointsForVideo(Video video) {
        return statistics.getVideoPoints(video);
    }

    public void givePointsForQuiz(int quizID, int points) {
        statistics.updateQuizPoints(quizID, points);
    }

    public void givePointsForViewingVideo(Video video, int points) {
        statistics.addVideoPoints(video, points);
    }

    @JsonIgnore
    public int getPoints() {
        return statistics.get(Statistics.Property.POINTS);
    }

    public void saveEmailToLocalStorage(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public static String getLoggedInUserEmail(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPref.getString("email", null);
    }
}
