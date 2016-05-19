package com.idgi.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.recycleViews.EmptyRecyclerView;
import com.idgi.core.Course;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.recycleViews.adapters.NameableAdapter;
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

        TextView textView = (TextView) findViewById(R.id.lesson_list_empty_view_text);
		if (textView != null)
        	textView.setText(getResources().getString(R.string.course_no_courses));

		List<Course> courses = SessionData.hasLoggedInUser() ? SessionData.getLoggedInUser().getMyCourses() : new ArrayList<Course>();

        EmptyRecyclerView recycler = (EmptyRecyclerView) findViewById(R.id.my_courses_list_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, courses);
		RecyclerViewUtility.connect(this, recycler, adapter);

		//emptyView is displayed if the RecyclerView has no elements
		View emptyView = findViewById(R.id.lesson_list_empty_view);
		if (recycler != null)
			recycler.setEmptyView(emptyView);
    }

}
