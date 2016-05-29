package com.idgi.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.android.recyclerview.EmptyRecyclerView;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.android.recyclerview.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;

/*
Shows the lessons of a course.
 */
public class CourseLessonListFragment extends Fragment {

    public CourseLessonListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nameable_list, container, false);

        initializeList(view);
        return view;
    }

    private void initializeList(View view) {
        List<? extends Nameable> lessons = SessionData.getCurrentCourse().getLessons();
        NameableAdapter adapter = new NameableAdapter(getContext(), lessons);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.nameable_list_recycler_view);
        RecyclerViewUtility.connect(getContext(), recycler, adapter);

        setupEmptyListLayout(view, recycler);
    }

    private void setupEmptyListLayout(View view, EmptyRecyclerView recycler) {
        View emptyView = view.findViewById(R.id.nameable_list_view_empty);
        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_lessons));
    }

}