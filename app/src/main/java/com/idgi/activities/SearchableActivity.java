package com.idgi.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Nameable;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.services.IDatabase;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchableActivity extends DrawerActivity {

    private ArrayList<Nameable> searchResults;

    private static final String ACTIVITY_TAG = "SEARCH_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        ApplicationBus.register(this);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchDatabase(query.toLowerCase(Locale.ENGLISH));
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.searchable_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, searchResults);
		RecyclerViewUtility.connect(this, recycler, adapter);

        String title = getResources().getString(R.string.search_title);
        super.initializeWithTitle(title);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        ApplicationBus.register(this);
    }

    private IDatabase getDatabase() {
        return FireDatabase.getInstance();
    }

    private void searchDatabase(String query){
        ArrayList<Nameable> results = new ArrayList<>();

        IDatabase database = getDatabase();
        List<School> schools = database.getSchools();

        for(School school: schools){
            if(school.getName().toLowerCase(Locale.ENGLISH).contains(query))
                results.add(school);

            for(Subject subject : school.getSubjects()){
                if(subject.getName().toLowerCase(Locale.ENGLISH).contains(query))
                    results.add(subject);

                for(Course course: subject.getCourses()){
                    if(course.getName().toLowerCase(Locale.ENGLISH).contains(query))
                        results.add(course);

                    for(Lesson lesson : course.getLessons()){
                        if(lesson.getName().toLowerCase(Locale.ENGLISH).contains(query))
                            results.add(lesson);
                    }
                }
            }
        }
        this.searchResults = results;
    }

    @Subscribe
    public void onSchoolSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.SCHOOL_SELECTED) {
            School school = (School) busEvent.getData();
            SessionData.setCurrentSchool(school);
            startActivity((new Intent(this, SubjectListActivity.class)));
            Log.d("search", "school");
        }
    }

    @Subscribe
    public void onSubjectSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.SUBJECT_SELECTED) {
            Subject subject = (Subject) busEvent.getData();
            SessionData.setCurrentSubject(subject);
            startActivity((new Intent(this, CourseListActivity.class)));
            Log.d("search", "subject");
        }
    }

    @Subscribe
    public void onCourseSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.COURSE_SELECTED) {
            Course course = (Course) busEvent.getData();
            SessionData.setCurrentCourse(course);
            startActivity((new Intent(this, CourseActivity.class)));
            Log.d("search", "course");
        }
    }

    @Subscribe
    public void onLessonSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.LESSON_SELECTED) {
            Lesson lesson = (Lesson) busEvent.getData();
            SessionData.setCurrentLesson(lesson);
            startActivity((new Intent(this, LessonActivity.class)));
            Log.d("search", "lesson");
        }
    }
}

