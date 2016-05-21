package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.User;
import com.idgi.recycleViews.adapters.UserListAdapter;
import com.idgi.services.FireDatabase;

import java.util.List;

/**
 * Created by tove on 2016-05-21.
 */
public class UserListActivity extends DrawerActivity {

    private Toolbar toolbar;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initializeUserList();
        String title = getResources().getString(R.string.list_user_name);
        super.initializeWithTitle(title);
    }

    private void initializeUserList() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_user_name));

        initializeDrawer();

        List<User> users = FireDatabase.getInstance().getUsers();

        adapter = new UserListAdapter(this, users);
        manager = new LinearLayoutManager(this);

        recycler = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        if (recycler != null) {
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(manager);
        }
    }
}
