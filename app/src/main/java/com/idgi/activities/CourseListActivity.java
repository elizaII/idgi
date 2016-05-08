package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.services.Database;
import com.idgi.services.IDatabase;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.recycleViews.adapters.CourseListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseListActivity extends AppCompatActivityWithDrawer {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private IDatabase database = Database.getInstance();

    private List<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("subjectName");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(s);

        initializeDrawer();

        courses = database.getCourses(null);
        ArrayList<String> courseNames = new ArrayList<>();

        Collections.sort(courseNames);

        for(Course course: courses){
            courseNames.add(course.getName());
        }

        manager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(this, courseNames);

        recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

    }
}
