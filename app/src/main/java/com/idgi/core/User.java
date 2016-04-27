package com.idgi.core;

/**
 * Created by Allex on 2016-04-27.
 */
public class User {
    private String name;
    private String eMail;
    private int phoneNumber;

    public User(String name){
        this.name=name;
    }
    public User(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getEMail(){
        return this.eMail;
    }
    public String getName(){
        return this.name;
    }
    public int getPhoneNumber(){
        return this.phoneNumber;
    }

}
