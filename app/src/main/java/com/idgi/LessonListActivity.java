package com.idgi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.idgi.core.Lesson;
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.LessonListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LessonListActivity extends AppCompatActivityWithDrawer {


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    //should be list of Lessons, when working
    private ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        initializeDrawer();

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("courseName");

        TextView txt = (TextView)findViewById(R.id.lesson_list_course);
        txt.setText(s);

        ArrayList<String> lessonNames = new ArrayList<>();
        List<Lesson> lessons = database.getLessons();

        for(Lesson lesson : lessons) {
            lessonNames.add(lesson.getName());
        }

        Collections.sort(lessonNames);

        manager = new LinearLayoutManager(this);
        adapter = new LessonListAdapter(this, lessonNames);

        recycler = (RecyclerView) findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
