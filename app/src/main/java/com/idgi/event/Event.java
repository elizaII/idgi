package com.idgi.event;

/*
 * The types of events that can be sent through the bus.
 */
public enum Event {
    QUIZ_TYPE_SELECTED, COURSE_SELECTED, LESSON_SELECTED,
    QUIZ_SELECTED, SCHOOL_SELECTED, SUBJECT_SELECTED, HAT_SELECTED,
    POINTS_UPDATED, START_ACTIVITY, NAMEABLE_SELECTED, LOGIN_REQUIRED_DIALOG,
    SHOW_MSG_COURSE_ADDED, SHOW_MSG_COURSE_REMOVED, QUESTION_ADDED
}
