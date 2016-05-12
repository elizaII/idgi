package com.idgi.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.idgi.R;
import com.idgi.activities.extras.AppCompatActivityWithDrawer;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.util.Nameable;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivityWithDrawer {

    private List<Nameable> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if(intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            query.toLowerCase();
            searchResults = searchDatabase(query);
        }
    }

    private List<Nameable> searchDatabase(String query){
        List<Nameable> results = new ArrayList<>();

        FireDatabase ref = FireDatabase.getInstance();
        List<School> schools = ref.getSchools();
        for(School school: schools){
            if(school.getName().toLowerCase().equals(query)){
                results.add(school);
            }
            for(Subject subject : school.getSubjects()){
                if(subject.getName().toLowerCase().equals(query)){
                    results.add(subject);
                }
                for(Course course: subject.getCourses()){
                    if(course.getName().toLowerCase().equals(query)){
                        results.add(course);
                    }
                    for(Lesson lesson : course.getLessons()){
                        if(lesson.getName().toLowerCase().equals(query)){
                            results.add(lesson);
                        }
                    }
                }
            }
        }
        return results;
    }
}

