package com.idgi.android.activity;

import android.content.Intent;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.Nameable;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.List;
/*
List of all courses in a subject
 */
public class CourseListActivity extends NameableListActivity {

    @Override
    protected String getTitleName() {
        return SessionData.getCurrentSubject().getName();
    }

    @Override
    protected List<? extends Nameable> getNameables() {
        return SessionData.getCurrentSubject().getCourses();
    }

    @Subscribe
    public void onCourseSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.COURSE_SELECTED){
            Course course = (Course) busEvent.getData();
            SessionData.setCurrentCourse(course);

            startActivity(new Intent(this, CourseActivity.class));
        }
    }

    @Subscribe
    public void onCourseAdded(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.SHOW_MSG_COURSE_ADDED) {
            Toast.makeText(this, R.string.course_list_course_added, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onCourseRemoved(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.SHOW_MSG_COURSE_REMOVED) {
            Toast.makeText(this, R.string.course_list_course_removed, Toast.LENGTH_SHORT).show();
        }
    }


}