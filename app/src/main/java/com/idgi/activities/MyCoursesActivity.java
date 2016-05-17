package com.idgi.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.recycleViews.EmptyRecyclerView;
import com.idgi.core.Course;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

		String title = getResources().getString(R.string.title_activity_my_course);
		initializeWithTitle(title);

        List<Course> courses = SessionData.getLoggedInUser().getMyCourses();

        TextView textView = (TextView) findViewById(R.id.lesson_list_empty_view_text);
		if (textView != null)
        	textView.setText(getResources().getString(R.string.course_no_courses));

        EmptyRecyclerView recycler = (EmptyRecyclerView) findViewById(R.id.my_courses_list_recycler_view);
		CourseListAdapter adapter = new CourseListAdapter(this, courses);
		RecyclerViewUtility.connect(this, recycler, adapter);

		//emptyView is displayed if the RecyclerView has no elements
		View emptyView = findViewById(R.id.lesson_list_empty_view);
		if (recycler != null)
			recycler.setEmptyView(emptyView);
    }

}
