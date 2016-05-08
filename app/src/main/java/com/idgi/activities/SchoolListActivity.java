package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.idgi.R;
import com.idgi.core.School;
import com.idgi.services.FireDatabase;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.recycleViews.adapters.SchoolListAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class SchoolListActivity extends AppCompatActivityWithDrawer {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        initializeDrawer();

        ArrayList<String> schoolNames = new ArrayList<>();

        for(School school: FireDatabase.getInstance().getSchools())
            schoolNames.add(school.getName());

        Collections.sort(schoolNames);

        manager = new LinearLayoutManager(this);
        adapter = new SchoolListAdapter(this, schoolNames);

        recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
