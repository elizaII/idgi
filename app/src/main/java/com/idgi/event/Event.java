package com.idgi.event;

import java.io.Serializable;

/*
 * The types of events that can be sent through the bus.
 */
public enum Event implements Serializable {
    QUIZ_TYPE_SELECTED, COURSE_SELECTED, LESSON_SELECTED,
    QUIZ_SELECTED, SCHOOL_SELECTED, SUBJECT_SELECTED, HAT_SELECTED,
    POINTS_UPDATED, START_ACTIVITY
}
