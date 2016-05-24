package com.idgi.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.android.recycleViews.EmptyRecyclerView;
import com.idgi.android.recycleViews.RecyclerViewUtility;
import com.idgi.android.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;


public class CourseQuizListFragment extends Fragment {

    public CourseQuizListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_quiz_list, container, false);

        initializeList(view);
        return view;
    }

    private void initializeList(View view) {
        List<Lesson> lessons = SessionData.getCurrentCourse().getLessons();
        ArrayList<IQuiz> quizzes = new ArrayList<>();

        for(Lesson lesson: lessons){
            IQuiz quiz = lesson.getQuiz();
            if (quiz != null)
                quizzes.add(quiz);
        }

        NameableAdapter adapter = new NameableAdapter(this.getContext(), quizzes);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);
        RecyclerViewUtility.connect(getContext(), recycler, adapter);

        setupEmptyListLayout(view, recycler);
    }

    private void setupEmptyListLayout(View view, EmptyRecyclerView recycler) {
        View emptyView = view.findViewById(R.id.lesson_list_empty_view);
        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_quizzes));
    }


}