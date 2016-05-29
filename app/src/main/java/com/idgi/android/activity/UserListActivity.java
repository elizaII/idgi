package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.android.recyclerview.adapters.NameableAdapter;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.core.User;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Activity that shows a list of the users.
*/

public class UserListActivity extends NameableListActivity {

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

        Collections.sort(users, Util.SORT_BY_NAME);

        RecyclerView.Adapter adapter = new NameableAdapter(this, users);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        RecyclerViewUtility.connect(this, recycler, adapter);
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.list_user_name);
    }

    @Override
    protected List<? extends Nameable> getNameables() {
        return FireDatabase.getInstance().getUsers();
    }
}
