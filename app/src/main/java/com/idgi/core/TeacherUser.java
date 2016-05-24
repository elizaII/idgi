package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("teacher")
public class TeacherUser extends User {
    private TeacherUser() {super();}

    public TeacherUser(String name) {
        super(name);
    }
}
