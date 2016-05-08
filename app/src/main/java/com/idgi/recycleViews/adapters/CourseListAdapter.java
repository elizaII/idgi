package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.activities.CourseActivity;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.util.Storage;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public CourseListAdapter(Context context, ArrayList<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.courseTextView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView courseTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            courseTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String courseName = courseTextView.getText().toString();
            Course course = Storage.getCurrentSubject().getCourse(courseName);
            Storage.setCurrentCourse(course);

            Context context = view.getContext();
            context.startActivity(new Intent(context, CourseActivity.class));
        }

    }
}
