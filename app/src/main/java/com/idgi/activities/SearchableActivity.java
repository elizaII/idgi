package com.idgi.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.Config;
import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Nameable;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.services.IDatabase;
import com.idgi.services.MockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchableActivity extends DrawerActivity {

    private ArrayList<Nameable> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchDatabase(query.toLowerCase(Locale.ENGLISH));
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.searchable_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, searchResults);
		RecyclerViewUtility.connect(this, recycler, adapter);

        initializeDrawer();
    }

    private IDatabase getDatabase() {
        return Config.firebaseMode == Config.FirebaseMode.ACTIVE ?
				FireDatabase.getInstance() : MockData.getInstance();
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
}

