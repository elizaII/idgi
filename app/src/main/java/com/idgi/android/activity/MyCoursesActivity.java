package com.idgi.android.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.recyclerview.EmptyRecyclerView;
import com.idgi.core.Course;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.android.recyclerview.adapters.NameableAdapter;
import com.idgi.core.Student;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;

/*
Shows the courses that a student has added to "My Courses".
 */
public class MyCoursesActivity extends DrawerActivity {

	private NameableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

		String title = getResources().getString(R.string.title_activity_my_course);
		initializeWithTitle(title);

        TextView textView = (TextView) findViewById(R.id.list_empty_view_text);
		if (textView != null)
        	textView.setText(getResources().getString(R.string.course_no_courses));

		Student student = SessionData.getUserAsStudent();

		List<Course> courses = student != null ? student.getMyCourses() : new ArrayList<Course>();

        EmptyRecyclerView recycler = (EmptyRecyclerView) findViewById(R.id.my_courses_list_recycler_view);
		adapter = new NameableAdapter(this, courses);
		RecyclerViewUtility.connect(this, recycler, adapter);

		//emptyView is displayed if the RecyclerView has no elements
		View emptyView = findViewById(R.id.lesson_list_empty_view);
		if (recycler != null)
			recycler.setEmptyView(emptyView);
    }

	@Subscribe
	public void onCourseRemoved(BusEvent busEvent) {
		if(busEvent.getEvent() == Event.SHOW_MSG_COURSE_REMOVED) {
			Toast.makeText(this, R.string.course_list_course_removed, Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		}
	}

}
