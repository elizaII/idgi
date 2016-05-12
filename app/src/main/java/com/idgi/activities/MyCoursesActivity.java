package com.idgi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.EmptyRecyclerView;
import com.idgi.core.Course;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.recycleViews.adapters.MyCoursesAdapter;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

import java.util.ArrayList;

public class MyCoursesActivity extends AppCompatActivityWithDrawer {

    private CourseListAdapter adapter;
    private EmptyRecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Course> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_my_course);

        initializeDrawer();
        this.data = new ArrayList<>();
        data = Storage.getActiveUser().getMyCourses();



        manager = new LinearLayoutManager(this);
        recycler = (EmptyRecyclerView) findViewById(R.id.my_courses_list_recycler_view);
        recycler.setLayoutManager(manager);

        View emptyView = findViewById(R.id.lesson_list_empty_view);

        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_courses));

        adapter = new CourseListAdapter(this, this.data);
        recycler.setAdapter(adapter);
    }

}
