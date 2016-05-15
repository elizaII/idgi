package com.idgi.recycleViews.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.User;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

public class CourseHolder extends RecyclerView.ViewHolder {
	public TextView courseTextView;
	public Button btnMyCourses;
	private CourseListAdapter adapter;
	public TextView courseInformationTextView;
	public TextView courseDescriptionTextView;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


	public CourseHolder(View view, CourseListAdapter adapter){
		super(view);
		view.setOnClickListener(onListItemClick);
		this.adapter = adapter;
		courseTextView = (TextView) view.findViewById(R.id.course_list_listitem_course_name);
		courseDescriptionTextView = (TextView) view.findViewById(R.id.courseDescriptionTextView);
		courseInformationTextView = (TextView) view.findViewById(R.id.courseInformationTextView);
		btnMyCourses = (Button) view.findViewById(R.id.course_list_listitem_course_my_courses_button);
		btnMyCourses.setOnClickListener(onMyCoursesClick);
		courseTextView.setOnClickListener(onListItemClick);
	}

	private final View.OnClickListener onListItemClick = new View.OnClickListener() {
		public void onClick(View view) {
			String courseName = courseTextView.getText().toString();
			Course course = SessionData.getCurrentSubject().getCourse(courseName);
			if (course == null)
				throw new IllegalStateException(String.format(Locale.ENGLISH,
						"Course with name %s should be in current subject, but isn't.", courseName));
			else {
				SessionData.setCurrentCourse(course);
				pcs.firePropertyChange("startCourseActivity", null, null);
			}
		}
	};

	private final View.OnClickListener onMyCoursesClick = new View.OnClickListener() {
		public void onClick(View view) {
			String courseName = courseTextView.getText().toString();
			Course course = SessionData.getCurrentSubject().getCourse(courseName);

			if (SessionData.hasLoggedInUser()) {
				User user = SessionData.getLoggedInUser();

				if (user.isInMyCourses(course)) {
					user.removeFromMyCourses(course);
					btnMyCourses.setText(R.string.add_to_my_courses);
					adapter.notifyDataSetChanged();
				} else {
					user.addToMyCourses(course);
					btnMyCourses.setText(R.string.added_to_my_courses);
				}
			}
		}
	};

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
}
