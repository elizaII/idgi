package com.idgi.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.idgi.R;
import com.idgi.activities.QuizActivity;
import com.idgi.core.Lesson;
import com.idgi.core.ModelUtility;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.EmptyRecyclerView;
import com.idgi.recycleViews.adapters.QuizListAdapter;
import com.idgi.session.SessionData;
import java.util.List;


public class CourseQuizListFragment extends Fragment implements NameableSelectionBus.Listener {

    private List<Lesson> lessons;

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
        lessons = SessionData.getCurrentCourse().getLessons();

        RecyclerView.LayoutManager recyclerManager = new LinearLayoutManager(this.getContext());
        QuizListAdapter recyclerAdapter = new QuizListAdapter(this.getContext(), lessons);
        recyclerAdapter.addListener(this);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);

        if(recycler != null) {
            recycler.setAdapter(recyclerAdapter);
            recycler.setLayoutManager(recyclerManager);
        }

        // why do we have this?
        View emptyView = view.findViewById(R.id.lesson_list_empty_view);
        recycler.setEmptyView(emptyView);

        return view;
    }

    @Override
    public void onNameableSelected(String name) {
        SessionData.setCurrentLesson(ModelUtility.findByName(SessionData.getCurrentCourse().getLessons(), name));
        startActivity(new Intent(this.getContext(), QuizActivity.class));
    }
}