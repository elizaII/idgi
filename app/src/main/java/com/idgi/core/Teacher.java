package com.idgi.core;

import com.fasterxml.jackson.annotation.JsonTypeName;

/*
The type of user that teachers will be. Teacher-related functionality
only available to this type.
 */
@JsonTypeName("teacher")
public class Teacher extends User {
    private Teacher() {super();}

    public Teacher(String name) {
        super(name);
    }

    @Override
    public NameableType getType() {
        return NameableType.TEACHER;
    }
}
