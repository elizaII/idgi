package com.idgi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.util.SchoolListAdapter;

public class SchoolListActivity extends AppCompatActivity {


    private RecyclerView schoolList_RecyclerView_recycler;
    private RecyclerView.Adapter schoolList_Adapter_adapter;
    private RecyclerView.LayoutManager schoolList_LayoutManager_lm;

    private String[] schools = {"Mikael Elias Gymnasium", "Hvitfeldska", "Ingrid Segerstedt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        schoolList_LayoutManager_lm = new LinearLayoutManager(this);
        schoolList_Adapter_adapter = new SchoolListAdapter(this, schools);

        schoolList_RecyclerView_recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        schoolList_RecyclerView_recycler.setAdapter(schoolList_Adapter_adapter);
        schoolList_RecyclerView_recycler.setLayoutManager(schoolList_LayoutManager_lm);
    }




}
