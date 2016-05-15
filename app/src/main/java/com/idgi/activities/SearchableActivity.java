package com.idgi.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Nameable;
import com.idgi.recycleViews.adapters.SearchableAdapter;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.MockData;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends DrawerActivity {

    private ArrayList<Nameable> searchResults;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchDatabase(query.toLowerCase());
        }


        manager = new LinearLayoutManager(this);
        adapter = new SearchableAdapter(this, searchResults);

        recycler = (RecyclerView) findViewById(R.id.searchable_recycler_view);
        if (recycler != null) {
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(manager);
        }

        initializeDrawer();
    }

    private void searchDatabase(String query){
        ArrayList<Nameable> results = new ArrayList<>();

        MockData ref = MockData.getInstance();
        List<School> schools = ref.getSchools();

        for(School school: schools){
            if(school.getName().toLowerCase().contains(query)){
                results.add(school);
            }
            for(Subject subject : school.getSubjects()){
                if(subject.getName().toLowerCase().contains(query)){
                    results.add(subject);
                }
                for(Course course: subject.getCourses()){
                    if(course.getName().toLowerCase().contains(query)){
                        results.add(course);
                    }
                    for(Lesson lesson : course.getLessons()){
                        if(lesson.getName().toLowerCase().contains(query)){
                            results.add(lesson);
                        }
                    }
                }
            }
        }
        this.searchResults = results;
    }
}

