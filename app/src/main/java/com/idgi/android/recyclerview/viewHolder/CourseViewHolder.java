package com.idgi.android.recyclerview.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.ModelUtility;
import com.idgi.core.Nameable;
import com.idgi.core.Student;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.Locale;

/*
Used to display a Course in a list.
 */
public class CourseViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.course_list_listitem_course;

	private TextView courseTextView, descriptionTextView, breadCrumbTextView;
	private Button addToCoursesButton;
	private Nameable nameable;

	public CourseViewHolder(View view){
		super(view);

		view.setOnClickListener(onViewClick);
	}

	@Override
	public void initialize() {
		courseTextView = (TextView) findViewById(R.id.row_text_view);
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
		setBreadCrumbFrom(course);

		updateAddToCoursesButton(course);
	}

	private void setName(String name) {
		courseTextView.setText(name);
	}

	private void setDescription(String description) {
		if (description != null && !description.isEmpty())
			descriptionTextView.setText(description);
		else
			descriptionTextView.setText(R.string.course_no_info);
	}

	private void updateAddToCoursesButton(Course course) {
		Student student = SessionData.getUserAsStudent();
		boolean hasCourse = student != null && student.hasCourse(course);
		addToCoursesButton.setSelected(hasCourse);
	}

	private void setBreadCrumbFrom(Course course) {
		String breadCrumb = String.format(Locale.ENGLISH, "%s > %s", course.getParentSchoolName(), course.getParentSubjectName());
		breadCrumbTextView.setText(breadCrumb);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent event = new BusEvent(Event.COURSE_SELECTED, nameable);
			ApplicationBus.post(event);
		}
	};

	private final View.OnClickListener onAddToCoursesClick = new View.OnClickListener() {
		public void onClick(View view) {
			if (!SessionData.hasLoggedInUser()) {
				BusEvent event = new BusEvent(Event.LOGIN_REQUIRED_DIALOG, null);
				ApplicationBus.post(event);
				return;
			}

			Button button = (Button) view;
			Student student = SessionData.getUserAsStudent();

			if (student != null) {
				Course course = (Course) nameable;

				button.setSelected(!student.hasCourse(course));
				updateStudentCourses(student, course);
			}
		}
	};

	private void updateStudentCourses(Student student, Course course) {
		if (student.hasCourse(course))
			removeCourse(course, student);
		else
			addCourse(course, student);
	}

	private void addCourse(Course course, Student student) {
		student.addToMyCourses(course);

		BusEvent event = new BusEvent(Event.SHOW_MSG_COURSE_ADDED, null);
		ApplicationBus.post(event);
	}

	private void removeCourse(Course course, Student student) {
		student.removeFromMyCourses(course);

		BusEvent event = new BusEvent(Event.SHOW_MSG_COURSE_REMOVED, null);
		ApplicationBus.post(event);
	}


	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new CourseViewHolder(view);
	}
}