package com.idgi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.EmptyRecyclerView;
import com.idgi.core.Lesson;
import com.idgi.services.MockData;
import com.idgi.recycleViews.adapters.LessonListAdapter;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CourseLessonListFragment extends Fragment {

    private EmptyRecyclerView recycler;
    private LessonListAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private List<Lesson> lessons;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CourseLessonListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_lesson_list, container, false);

        lessons = SessionData.getCurrentCourse().getLessons();


        recyclerManager = new LinearLayoutManager(this.getContext());
        recyclerAdapter = new LessonListAdapter(this.getContext(), lessons);
		recyclerAdapter.setPropertyChangeSupport(pcs);

        recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(recyclerManager);

        View emptyView = view.findViewById(R.id.lesson_list_empty_view);

        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_lessons));

        return view;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}