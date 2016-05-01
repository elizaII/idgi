package com.idgi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.idgi.core.Lesson;
import com.idgi.services.Database;
import com.idgi.util.LessonListAdapter;

import java.util.ArrayList;

public class LessonListActivity extends AppCompatActivity {


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    private ArrayList<String> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("courseName");

        String lesson = "matte 3c";
        lessons.add(lesson);

        TextView txt = (TextView)findViewById(R.id.lesson_list_course);
        txt.setText(s);


        manager = new LinearLayoutManager(this);
        adapter = new LessonListAdapter(this, lessons);

        recycler = (RecyclerView) findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
