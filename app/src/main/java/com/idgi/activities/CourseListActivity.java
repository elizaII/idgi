package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.ModelUtility;
import com.idgi.core.Subject;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.CourseListAdapter;
import com.idgi.session.SessionData;

import java.util.List;

public class CourseListActivity extends DrawerActivity implements NameableSelectionBus.Listener {
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        String title = SessionData.getCurrentSubject().getName();
        super.initializeWithTitle(title);

        initializeCourseList();
    }

    private void initializeCourseList() {
        Subject subject = SessionData.getCurrentSubject();
        courses = subject.getCourses();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.course_list_recycler_view);
        CourseListAdapter adapter = new CourseListAdapter(this, courses);
        adapter.addListener(this);

        RecyclerViewUtility.connect(this, recycler, adapter);
    }
/*Why actionbar?
    private void initializeToolbar() {
        String title = SessionData.getCurrentSubject().getName();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }
*/
    @Override
    public void onNameableSelected(String name) {
        Course course = ModelUtility.findByName(courses, name);
        SessionData.setCurrentCourse(course);

        startActivity(new Intent(this, CourseActivity.class));
    }
}
