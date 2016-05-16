package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.ModelUtility;
import com.idgi.core.Subject;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

import java.util.List;

public class CourseListActivity extends DrawerActivity implements NameableSelectionBus.Listener {
    private List<Course> courses;
    private NameableSelectionBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        initializeToolbar();
        initializeDrawer();
        initializeCourseList();
    }

    private void initializeCourseList() {
        Subject subject = SessionData.getCurrentSubject();
        courses = subject.getCourses();

        CourseListAdapter adapter = new CourseListAdapter(this, courses);

        adapter.addListener(this);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        if (recycler != null) {
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void initializeToolbar() {
        String title = getResources().getString(R.string.list_school_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onNameableSelected(String name) {
        Course course = ModelUtility.findByName(FireDatabase.getInstance().getCourses(SessionData.getCurrentSubject()), name);
        SessionData.setCurrentCourse(course);

        startActivity(new Intent(this, CourseActivity.class));
    }
}
