package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Hat;
import com.idgi.core.StudentUser;
import com.idgi.recycleViews.adapters.HatListAdapter;
import com.idgi.services.FireDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hat_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_my_hats));

        initializeDrawer();

        StudentUser user = (StudentUser) SessionData.getLoggedInUser();

        adapter = new HatListAdapter(this, user.getHats());

        manager = new LinearLayoutManager(this);

        recycler = (RecyclerView) findViewById(R.id.hat_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);
    }
}
