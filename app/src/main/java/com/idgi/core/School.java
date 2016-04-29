package com.idgi.core;

/**
 * Created by Emil on 29/04/2016.
 */
public class School {
    private String key;
    private String value;

    public School(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public String getValue() {
        return value;
    }
}
