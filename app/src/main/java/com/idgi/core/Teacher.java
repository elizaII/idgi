package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public class Teacher extends User {
    private Teacher() {super();}

    public Teacher(String name) {
        super(name);
    }
}
