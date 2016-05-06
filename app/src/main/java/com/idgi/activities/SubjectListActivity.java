package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.Subject;
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.recycleViews.adapters.SubjectListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectListActivity extends AppCompatActivityWithDrawer{
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    private List<Subject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("schoolName");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(s);

        initializeDrawer();

        subjects = database.getSubjects();
        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects){
            subjectNames.add(subject.getValue());
        }

        Collections.sort(subjectNames);


        manager = new LinearLayoutManager(this);
        adapter = new SubjectListAdapter(this, subjectNames);

        recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);


    }
}
