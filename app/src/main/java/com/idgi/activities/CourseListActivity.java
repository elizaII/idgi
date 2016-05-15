package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.activities.extras.ActivityType;
import com.idgi.core.Course;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

public class CourseListActivity extends DrawerActivity implements PropertyChangeListener {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        String subjectName = SessionData.getCurrentSubject().getName();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null)
            toolbar.setTitle(subjectName);

        initializeDrawer();

        courses = SessionData.getCurrentSchool().getSubject(subjectName).getCourses();

        ArrayList<String> courseNames = new ArrayList<>();
        for(Course course: courses)
            courseNames.add(course.getName());

        Collections.sort(courseNames);

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        manager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(this, courses);
        ((CourseListAdapter) adapter).addPropertyChangeListener(this);

        recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        if (recycler != null) {
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(manager);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("startCourseActivity"))
            startActivity(new Intent(this, CourseActivity.class));
    }
}
