package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.android.recycleView.RecyclerViewUtility;
import com.idgi.android.recycleView.adapters.NameableAdapter;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;

import java.util.List;

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
}