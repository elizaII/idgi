package com.idgi.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.android.recyclerview.adapters.NameableAdapter;
import com.idgi.core.Student;
import com.idgi.core.User;
import com.idgi.android.recyclerview.EmptyRecyclerView;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Presents the users that have saved the selected course
 */
public class CourseUserListFragment extends Fragment {

    public CourseUserListFragment() {
        //Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nameable_list, container, false);

        List<User> users = new ArrayList<>();
        for (User user : FireDatabase.getInstance().getUsers()) {
            if (user.isStudent()) {
                Student student = (Student) user;
                if (student.getMyCourses().contains(SessionData.getCurrentCourse())) {
                    users.add(student);
                }
            }
        }

        Collections.sort(users, Util.SORT_BY_NAME);

        NameableAdapter adapter = new NameableAdapter(getContext(), users);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.nameable_list_recycler_view);
        RecyclerViewUtility.connect(getContext(), recycler, adapter);

        View emptyView = view.findViewById(R.id.nameable_list_view_empty);
        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_users));

        return view;
    }
}