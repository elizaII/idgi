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
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.EmptyRecyclerView;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


public class CourseQuizListFragment extends Fragment {
    private NameableSelectionBus bus;

    public CourseQuizListFragment() {
        bus = new NameableSelectionBus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_quiz_list, container, false);
        List<Lesson> lessons = SessionData.getCurrentCourse().getLessons();
        ArrayList<IQuiz> quizzes = new ArrayList<>();

        for(Lesson lesson: lessons){
            IQuiz quiz = lesson.getQuiz();
            if (quiz != null)
                quizzes.add(quiz);
        }

        NameableAdapter adapter = new NameableAdapter(this.getContext(), quizzes, this.bus);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);
        RecyclerViewUtility.connect(getContext(), recycler, adapter);

        View emptyView = view.findViewById(R.id.lesson_list_empty_view);
        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_quizzes));

        return view;
    }

    public void addListener(NameableSelectionBus.Listener listener) {
        bus.addListener(listener);
    }

}