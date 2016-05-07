package com.idgi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.services.Database;
import com.idgi.recycleViews.adapters.LessonListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CourseLessonListFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerManager;

    private Database database = Database.getInstance();

    private List<Lesson> lessons;

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

        ArrayList<String> lessonNames = new ArrayList<>();
        lessons = database.getLessons(null);

        for(Lesson lesson : lessons) {
            lessonNames.add(lesson.getName());
        }

        Collections.sort(lessonNames);

        recyclerManager = new LinearLayoutManager(this.getContext());
        recyclerAdapter = new LessonListAdapter(this.getContext(), lessonNames);

        recycler = (RecyclerView) this.getActivity().findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(recyclerManager);

        return view;
    }

}

