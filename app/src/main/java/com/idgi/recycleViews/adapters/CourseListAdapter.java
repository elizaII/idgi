package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.activities.CourseActivity;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private ArrayList<Course> data;
    private LayoutInflater inflater;


    public CourseListAdapter(Context context, ArrayList<Course> data){
        this.data = new ArrayList<>();
        this.data=data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.courseTextView.setText(data.get(position).getName());
        if (Storage.hasActiveUser()) {
            if (Storage.getActiveUser().getMyCourses().contains(data.get(position))){
                holder.isAddedToMyCourses=true;
                holder.my_courses_button.setText(R.string.added_to_my_courses);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView courseTextView;
        public Button my_courses_button;
        protected boolean isAddedToMyCourses = false;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            courseTextView =(TextView) v.findViewById(R.id.rowTextView);
            my_courses_button = (Button) v.findViewById(R.id.my_courses_button);
            my_courses_button.setOnClickListener(this);
            courseTextView.setOnClickListener(this);
        }

        public void onClick(View view){
            String courseName = courseTextView.getText().toString();
            Course course = Storage.getCurrentSubject().getCourse(courseName);
            switch (view.getId()) {
                case R.id.rowTextView:
                    Storage.setCurrentCourse(course);
                    Context context = view.getContext();
                    context.startActivity(new Intent(context, CourseActivity.class));
                    break;
                case R.id.my_courses_button:
                    if (Storage.hasActiveUser()){
                        if (isAddedToMyCourses){
                            Storage.getActiveUser().removeFromMyCourses(course);
                            my_courses_button.setText(R.string.add_to_my_courses);
                            isAddedToMyCourses=false;
                        }
                        else {
                            Storage.getActiveUser().addToMyCourses(course);
                            my_courses_button.setText(R.string.added_to_my_courses);
                            isAddedToMyCourses = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
