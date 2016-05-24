package com.idgi.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.android.recycleView.EmptyRecyclerView;
import com.idgi.android.recycleView.RecyclerViewUtility;
import com.idgi.android.recycleView.adapters.UserListAdapter;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tove on 2016-05-21.
 */
public class CourseUserListFragment extends Fragment {

    public CourseUserListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_user_list, container, false);

        List<User> users = new ArrayList<>();
        for (User user : FireDatabase.getInstance().getUsers()) {
            if (user.getMyCourses().contains(SessionData.getCurrentCourse())) {
                users.add(user);
            }
        }

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        UserListAdapter adapter = new UserListAdapter(getContext(), users);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.course_user_list_recycler_view);
        RecyclerViewUtility.connect(getContext(), recycler, adapter);

        View emptyView = view.findViewById(R.id.course_user_list_empty_view);
        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_users));

        return view;
    }
}