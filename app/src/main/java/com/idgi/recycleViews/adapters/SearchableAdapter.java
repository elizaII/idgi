package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.CourseActivity;
import com.idgi.activities.CourseListActivity;
import com.idgi.activities.LessonActivity;
import com.idgi.activities.SchoolListActivity;
import com.idgi.activities.SubjectListActivity;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.ModelUtility;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.Nameable;

import java.util.ArrayList;

/**
 * Created by Emil on 12/05/2016.
 */
public class SearchableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Nameable> data;

    private final int SCHOOL = 0, SUBJECT = 1, COURSE = 2, LESSON = 3;

    private LayoutInflater inflater;

    public SearchableAdapter(Context context, ArrayList<Nameable>data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof School) {
            return SCHOOL;
        } else if (data.get(position) instanceof Subject) {
            return SUBJECT;
        }
        else if (data.get(position) instanceof Course) {
            return COURSE;
        }
        else if (data.get(position) instanceof Lesson) {
            return LESSON;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case SCHOOL:
                View v1 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolderSchool(v1);
                break;
            case SUBJECT:
                View v2 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolderSubject(v2);
                break;
            case COURSE:
                View v3 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolderCourse(v3);
                break;
            case LESSON:
                View v4 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolderLesson(v4);
                break;
            default:
                //TODO some other default
                View v = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolderSchool(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case SCHOOL:
                ViewHolderSchool holder1 = (ViewHolderSchool) holder;
                School school = (School) data.get(position);
                holder1.searchTextView.setText(school.getName());
                break;
            case SUBJECT:
                ViewHolderSubject holder2 = (ViewHolderSubject) holder;
                Subject subject = (Subject) data.get(position);
                holder2.searchTextView.setText(subject.getName());
                break;
            case COURSE:
                ViewHolderCourse holder3 = (ViewHolderCourse) holder;
                Course course = (Course) data.get(position);
                holder3.searchTextView.setText(course.getName());
                break;
            case LESSON:
                ViewHolderLesson holder4 = (ViewHolderLesson) holder;
                Lesson lesson = (Lesson) data.get(position);
                holder4.searchTextView.setText(lesson.getName());
                break;
            default:

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolderSchool extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolderSchool(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String schoolName = searchTextView.getText().toString();
            School school = FireDatabase.getInstance().getSchool((schoolName));

            SessionData.setCurrentSchool(school);

            Context context = view.getContext();
            context.startActivity(new Intent(context, SubjectListActivity.class));
        }
    }

    public static class ViewHolderSubject extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolderSubject(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String subjectName = searchTextView.getText().toString();
            Subject subject = ModelUtility.findByName(FireDatabase.getInstance().getSubjects(SessionData.getCurrentSchool()), subjectName);

            SessionData.setCurrentSubject(subject);

            Context context = view.getContext();
            context.startActivity(new Intent(context, CourseListActivity.class));
        }
    }

    public static class ViewHolderCourse extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolderCourse(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String courseName = searchTextView.getText().toString();
            Course course = ModelUtility.findByName(FireDatabase.getInstance().getCourses(SessionData.getCurrentSubject()), courseName);

            SessionData.setCurrentCourse(course);

            Context context = view.getContext();
            context.startActivity(new Intent(context, CourseActivity.class));
        }
    }

    public static class ViewHolderLesson extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolderLesson(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view) {
            String lessonName = searchTextView.getText().toString();
            Lesson lesson = ModelUtility.findByName(FireDatabase.getInstance().getLessons(SessionData.getCurrentCourse()), lessonName);

            SessionData.setCurrentLesson(lesson);

            Context context = view.getContext();
            context.startActivity(new Intent(context, LessonActivity.class));
        }
    }
}