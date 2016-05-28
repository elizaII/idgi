package com.idgi.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
        @JsonSubTypes.Type(value = Student.class, name = "student"),
        @JsonSubTypes.Type(value = Teacher.class, name = "teacher")
})
public abstract class User implements Nameable {
    private String name;
    private String phoneNumber;
    private int age;

    @JsonIgnore
    private Bitmap profilePicture;

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

    @JsonIgnore
    public void setProfilePicture(Bitmap profilePicture) {
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
    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    @Override
    public NameableType getType() {
        return NameableType.USER;
    }
}
