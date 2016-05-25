package com.idgi.android.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.android.recycleView.RecyclerViewUtility;
import com.idgi.android.recycleView.adapters.NameableAdapter;
import com.idgi.core.User;
import com.idgi.service.FireDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserListActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        String title = getResources().getString(R.string.list_user_name);
        super.initializeWithTitle(title);

        initializeUserList();
    }

    private void initializeUserList() {
        List<User> users = FireDatabase.getInstance().getUsers();

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        RecyclerView.Adapter adapter = new NameableAdapter(this, users);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        RecyclerViewUtility.connect(this, recycler, adapter);
    }
}
