package com.idgi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.recycleViews.adapters.MyCoursesAdapter;
import com.idgi.util.Storage;

import java.util.ArrayList;

public class MyCoursesActivity extends AppCompatActivity {

    private CourseListAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Course> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        this.data = new ArrayList<>();
        data = Storage.getActiveUser().getMyCourses();

        manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_courses_list_recycler_view);
        recyclerView.setLayoutManager(manager);

        adapter = new CourseListAdapter(this, this.data);
        recyclerView.setAdapter(adapter);
    }

}
