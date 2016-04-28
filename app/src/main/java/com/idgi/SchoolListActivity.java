package com.idgi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.util.SchoolListAdapter;

public class SchoolListActivity extends AppCompatActivity {


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private String[] schools = {"Mikael Elias Gymnasium", "Hvitfeldska", "Ingrid Segerstedt", "hej", "jonathan", "hur", "mår","du", "idag",
                            "din", "skojare", "helluuu", "lololol", "trololol", "abababa", "asjndjasn", "aksdkasmdkam", "asldalskdm", "asdlkasm", "askmdksmd",
                            "askdmaksdm", "asdkmalksm", "asödaög", "asldjnasjknd", "aslkdaslkmd", "askldmaslkdm", "askmdkasm", "sadlkams", "aslkdmalsm", "asdlkmasldk", "gogogo",
                            "askdmaksm", "askmdla", "asdkmaslk", "askmdlaksmdl", "laksmdlakm", "askdmaksdn"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);

        manager = new LinearLayoutManager(this);
        adapter = new SchoolListAdapter(this, schools);

        recycler = (RecyclerView) findViewById(R.id.school_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }




}
