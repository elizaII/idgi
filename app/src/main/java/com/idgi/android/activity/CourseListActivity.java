package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.android.recycleView.RecyclerViewUtility;
import com.idgi.android.recycleView.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;

public class CourseListActivity extends DrawerActivity {
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        String title = SessionData.getCurrentSubject().getName();
        super.initializeWithTitle(title);

        initializeCourseList();
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
