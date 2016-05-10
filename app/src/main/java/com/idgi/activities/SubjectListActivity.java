package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.Database;
import com.idgi.services.FireDatabase;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.recycleViews.adapters.SubjectListAdapter;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectListActivity extends AppCompatActivityWithDrawer{
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private List<Subject> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Solve this in a better way instead of just going back to the previous activity.
        if (Storage.getCurrentSchool() == null) {
            System.out.println(":::::::NOTE:::::: Can not enter SubjectListActivity because Storage.getCurrentSchool() returns null");
            finish();
        }

        setContentView(R.layout.activity_subject_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeDrawer();


        manager = new LinearLayoutManager(this);
        adapter = new SubjectListAdapter(this, getSubjectNames());

        recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }

    private ArrayList<String> getSubjectNames() {
        School school = Storage.getCurrentSchool();
        getSupportActionBar().setTitle(school.getName());

        subjects = school.getSubjects();

        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject subject: subjects)
            subjectNames.add(subject.getName());

        Collections.sort(subjectNames);

        return subjectNames;
    }
}
