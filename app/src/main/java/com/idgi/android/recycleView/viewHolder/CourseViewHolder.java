package com.idgi.android.recycleView.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.ModelUtility;
import com.idgi.core.Nameable;
import com.idgi.core.User;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.Locale;

public class CourseViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.course_list_listitem_course;

	private TextView courseTextView, descriptionTextView, breadCrumbTextView;
	private Button addToCoursesButton;
	private Nameable nameable;

	public CourseViewHolder(View view){
		super(view);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent nameableEvent = new BusEvent(Event.COURSE_SELECTED, nameable);
			postToBus(nameableEvent);
		}
	};

	private final View.OnClickListener onAddToCoursesClick = new View.OnClickListener() {
		public void onClick(View view) {
			Button button = (Button) view;

			if (SessionData.hasLoggedInUser()) {
				User currentUser = SessionData.getLoggedInUser();
				String courseName = courseTextView.getText().toString();
				Course course = ModelUtility.findByName(currentUser.getMyCourses(), courseName);

				if (course != null) {
					currentUser.removeFromMyCourses(course);
					button.setText(R.string.add_to_my_courses);
					// TODO Subscribe adapter to events of this class and implement sending
					// TODO of events in this class, so adapter can update itself when necessary.
					//adapter.notifyDataSetChanged();
				} else {
					currentUser.addToMyCourses(course);
					button.setText(R.string.added_to_my_courses);
				}
			}
		}
	};

	@Override
	public void initialize() {
		courseTextView = (TextView) findViewById(R.id.rowTextView);
		descriptionTextView = (TextView) findViewById(R.id.courseDescriptionTextView);
		breadCrumbTextView = (TextView) findViewById(R.id.courseInformationTextView);
		addToCoursesButton = (Button) findViewById(R.id.course_list_listitem_course_my_courses_button);
		addToCoursesButton.setOnClickListener(onAddToCoursesClick);
	}

	@Override
	public void bind(Nameable nameable) {
		this.nameable = nameable;
		Course course = (Course) nameable;

		setName(course.getName());
		setDescription(course.getDescription());
		setBreadCrumb(course);

		if (SessionData.hasLoggedInUser())
			updateAddToCoursesButton(SessionData.getLoggedInUser(), course);
	}

	private void setName(String name) {
		courseTextView.setText(name);
	}

	private void setDescription(String description) {
		if (description == null)
			descriptionTextView.setText(R.string.no_description);
		else
			descriptionTextView.setText(description);
	}

	private void updateAddToCoursesButton(User user, Course course) {
		boolean hasCourse = user.hasCourse(course);
		int resource = hasCourse ? R.string.added_to_my_courses : R.string.log_in_message;

		addToCoursesButton.setText(resource);
		addToCoursesButton.setEnabled(hasCourse);
	}

	private void setBreadCrumb(Course course) {
		String breadCrumb = String.format(Locale.ENGLISH, "%s > %s", course.getParentSchool(), course.getParentSubject());
		breadCrumbTextView.setText(breadCrumb);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new CourseViewHolder(view);
	}
}