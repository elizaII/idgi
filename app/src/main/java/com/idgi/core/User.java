package com.idgi.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StudentUser.class, name = "student"),
        @JsonSubTypes.Type(value = TeacherUser.class, name = "teacher")
})
public abstract class User {
    private String name;
    private String email;
    private String phoneNumber;
    private int age;

    @JsonIgnore
    private Drawable profilePicture;

    private ArrayList<Course> myCourses;

    public User() {
        myCourses = new ArrayList<>();
    }

	public User(String name){
		this.name = name;

        myCourses = new ArrayList<>();
	}

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public void setProfilePicture(Drawable profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addToMyCourses(Course course){
        myCourses.add(course);
    }

    public void removeFromMyCourses(Course course){
        myCourses.remove(course);
    }

    public ArrayList<Course> getMyCourses() {
        return myCourses;
    }

    public void setMyCourses(ArrayList<Course> myCourses) {
        this.myCourses = myCourses;
    }

    public boolean hasCourse(Course course) {
        return getMyCourses().contains(course);
    }

    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    @JsonIgnore
    public Drawable getProfilePicture() {
        return profilePicture;
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
