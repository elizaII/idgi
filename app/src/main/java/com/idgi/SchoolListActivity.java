package com.idgi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.core.School;
import com.idgi.services.Database;
import com.idgi.util.SchoolListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchoolListActivity extends AppCompatActivity {


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    private ArrayList<String> schools = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        School s1 = database.getSchool("1");
        School s2 = database.getSchool("2");
        School s3 = database.getSchool("3");
        School s4 = database.getSchool("4");
        School s5 = database.getSchool("5");
        School s6 = database.getSchool("6");

        schools.add(s1.getValue());
        schools.add(s2.getValue());
        schools.add(s3.getValue());
        schools.add(s4.getValue());
        schools.add(s5.getValue());
        schools.add(s6.getValue());



        manager = new LinearLayoutManager(this);
        adapter = new SchoolListAdapter(this, schools);

        recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }




}
