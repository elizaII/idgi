package com.idgi.core;

public class User {
    private String name;
    private String email;
    private int phoneNumber;

    private Statistics statistics;

    public User(String name){
        this.name = name;
		this.statistics = new Statistics();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }
    public int getPhoneNumber(){
        return this.phoneNumber;
    }

    public int getStat(Statistics.Property property) {
        return statistics.get(property);
    }

}
