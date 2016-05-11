package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.services.Database;
import com.idgi.services.FireDatabase;
import com.idgi.services.IDatabase;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.util.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseListActivity extends AppCompatActivityWithDrawer {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        String subjectName = Storage.getCurrentSubject().getName();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(subjectName);

        initializeDrawer();

        courses = Storage.getCurrentSchool().getSubject(subjectName).getCourses();

        ArrayList<String> courseNames = new ArrayList<>();
        for(Course course: courses)
            courseNames.add(course.getName());

        Collections.sort(courseNames);

        manager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(this, courses);

        recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

    }

}
