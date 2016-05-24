package com.idgi.android;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;

import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.service.FireDatabase;
import com.idgi.service.IDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Manual workaround for search suggestions without
 * SQL-database.
 */
public class SearchSuggestions {

    private static SimpleCursorAdapter adapter;

    public static void initiateSearchSuggestions(final SearchView searchView) {
        final String[] from = new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1};
        final int[] to = new int[]{android.R.id.text1};
        adapter = new SimpleCursorAdapter(searchView.getContext(),
                android.R.layout.simple_list_item_2,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        searchView.setSuggestionsAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Not used
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //provide adapter with data
                populateAdapter(newText);
                return true;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                String query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                searchView.setQuery(query, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
    }

    //Provides the adapter with a cursor with correct data
    private static void populateAdapter(String query){
        final MatrixCursor cursor = new MatrixCursor(new String[] {"_id", SearchManager.SUGGEST_COLUMN_TEXT_1});
        IDatabase database = getDatabase();

        //Objects matching the started query
        ArrayList<Nameable> matches = new ArrayList<>();

        List<School> schools = database.getSchools();
        for (School school : schools) {
            if (school.getName().toLowerCase(Locale.ENGLISH).startsWith(query)){
                matches.add(school);
            }

            for (Subject subject : school.getSubjects()) {
                if (subject.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
                    matches.add(subject);

                for (Course course : subject.getCourses()) {
                    if (course.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
                        matches.add(course);

                    for (Lesson lesson : course.getLessons()) {
                        if (lesson.getName().toLowerCase(Locale.ENGLISH).startsWith(query))
                            matches.add(lesson);
                    }
                }
            }
        }

        Integer i = 0;

        for(Nameable nameable : matches){
            ArrayList<String> row = new ArrayList<>();
            row.add(i.toString());
            row.add(nameable.getName());
            i++;
            cursor.addRow(row);
        }

        cursor.moveToFirst();
        adapter.swapCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    private static IDatabase getDatabase() {
        return FireDatabase.getInstance();
    }
}
