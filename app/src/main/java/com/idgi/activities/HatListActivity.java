package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Hat;
import com.idgi.recycleViews.adapters.HatListAdapter;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by tove on 2016-05-15.
 */
public class HatListActivity extends DrawerActivity {
    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private ArrayList<Hat> hats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hat_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_my_hats));

        initializeDrawer();

        Hat hat1 = new Hat();
        hat1.setImageId(R.drawable.hat_pink);
        hat1.setName("Snygghatt");
        hat1.setDescription("Den bästa hatten du kan få");
        hat1.setPoints(1000);
        hats.add(hat1);

        Hat hat2 = new Hat();
        hat2.setImageId(R.drawable.hat_black);
        hat2.setName("Vanlig hatt");
        hat2.setDescription("En helt vanlig hatt");
        hat2.setPoints(100);
        hats.add(hat2);

        adapter = new HatListAdapter(this, hats);

        manager = new LinearLayoutManager(this);

        recycler = (RecyclerView) findViewById(R.id.hat_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
