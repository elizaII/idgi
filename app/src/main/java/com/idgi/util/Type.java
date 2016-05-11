package com.idgi.util;

public enum Type {
    SCHOOL, SUBJECT, COURSE;

    /* Returns a readable version of the type: School, Subject or Course */
    public String toString() {
        switch (this) {
            case SCHOOL:
                return "skola";
            case SUBJECT:
                return "ämne";
            case COURSE:
                return "kurs";
        }

        return null;
    }
}
