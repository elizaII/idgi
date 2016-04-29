package com.idgi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.util.CourseListAdapter;
import com.idgi.util.SubjectListAdapter;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<String> lessons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);


        manager = new LinearLayoutManager(this);
        adapter = new CourseListAdapter(this, lessons);

        String s = "hej";
        lessons.add(s);

        recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

    }
}
