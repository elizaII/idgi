package com.idgi.android.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.android.recycleView.adapters.UserListAdapter;
import com.idgi.service.FireDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tove on 2016-05-21.
 */
public class UserListActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initializeUserList();
        String title = getResources().getString(R.string.list_user_name);
        super.initializeWithTitle(title);
    }

    private void initializeUserList() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_user_name));

        initializeDrawer();

        List<User> users = FireDatabase.getInstance().getUsers();

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        RecyclerView.Adapter adapter = new UserListAdapter(this, users);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        if (recycler != null) {
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(manager);
        }
    }
}
