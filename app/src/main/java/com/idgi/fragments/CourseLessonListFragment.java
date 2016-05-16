package com.idgi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.EmptyRecyclerView;
import com.idgi.core.Lesson;
import com.idgi.recycleViews.adapters.LessonListAdapter;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;


public class CourseLessonListFragment extends Fragment {

    private List<Lesson> lessons;

	private NameableSelectionBus bus;

    public CourseLessonListFragment() {
		bus = new NameableSelectionBus();
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

        NameableAdapter recyclerAdapter = new LessonListAdapter(this.getContext(), lessons);
		recyclerAdapter.setBus(this.bus);

        EmptyRecyclerView recycler = (EmptyRecyclerView) view.findViewById(R.id.lesson_list_recycler_view);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        View emptyView = view.findViewById(R.id.lesson_list_empty_view);

        recycler.setEmptyView(emptyView);

        TextView textView = (TextView) view.findViewById(R.id.lesson_list_empty_view_text);
        textView.setText(getResources().getString(R.string.course_no_lessons));

        return view;
    }

    public void addListener(NameableSelectionBus.Listener listener) {
        bus.addListener(listener);
    }
}