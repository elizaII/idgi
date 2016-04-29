package com.idgi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.idgi.core.Subject;
import com.idgi.services.Database;
import com.idgi.util.SubjectListAdapter;

import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private Database database = Database.getInstance();

    private ArrayList<String> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        Bundle bundle = getIntent().getExtras();
        String s = bundle.getString("schoolName");

        TextView txt = (TextView)findViewById(R.id.subject_list_school);
        txt.setText(s);

        Subject math = database.getSubject("1");
        subjects.add(math.getValue());

        manager = new LinearLayoutManager(this);
        adapter = new SubjectListAdapter(this, subjects);

        recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
