package com.idgi;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.idgi.core.Subject;
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Navigation;
import com.idgi.util.SubjectListAdapter;

import java.lang.reflect.Array;
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

        initializeDrawer();

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("schoolName");

        TextView txt = (TextView)findViewById(R.id.subject_list_school);
        txt.setText(s);

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

        toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);

    }
}
