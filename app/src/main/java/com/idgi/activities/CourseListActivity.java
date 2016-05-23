package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.idgi.application.Application;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;

public class CourseListActivity extends DrawerActivity {
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        //Subscribe for course clicks
        ApplicationBus.register(this);

        String title = SessionData.getCurrentSubject().getName();
        super.initializeWithTitle(title);

        initializeCourseList();
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        ApplicationBus.register(this);
    }

    private void initializeCourseList() {
        courses = SessionData.getCurrentSubject().getCourses();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        NameableAdapter adapter = new NameableAdapter(this, courses);

        RecyclerViewUtility.connect(this, recycler, adapter);
    }

    @Subscribe
    public void onCourseSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.COURSE_SELECTED) {
            Course course = (Course) busEvent.getData();
            SessionData.setCurrentCourse(course);
            startActivity(new Intent(this, CourseActivity.class));
        }
    }
}
