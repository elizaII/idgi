package com.idgi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.Widgets.EmptyRecyclerView;
import com.idgi.core.Quiz;
import com.idgi.recycleViews.adapters.LessonListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CourseQuizListFragment extends Fragment {

    public CourseQuizListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_quiz_list, container, false);

        ArrayList<String> quizIds = new ArrayList<>();

        // TODO add actual quizzes
        List<Quiz> quizzes = Arrays.asList(new Quiz(), new Quiz());

        for(Quiz quiz : quizzes)
            quizIds.add(quiz.getID());

        Collections.sort(quizIds);

        RecyclerView.LayoutManager recyclerManager = new LinearLayoutManager(this.getContext());
        RecyclerView.Adapter recyclerAdapter = new LessonListAdapter(this.getContext(), quizIds);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(recyclerManager);

        View emptyView = view.findViewById(R.id.lesson_list_empty_view);

        recycler.setEmptyView(emptyView);

        return view;
    }

}