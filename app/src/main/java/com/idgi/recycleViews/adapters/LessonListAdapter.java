package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.activities.LessonActivity;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.session.SessionData;

import java.util.ArrayList;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public LessonListAdapter(Context context, ArrayList<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public LessonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonListAdapter.ViewHolder holder, int position) {
        holder.lessonTextView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lessonTextView;
        public ImageView lessonThumbnail;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            lessonTextView = (TextView) view.findViewById(R.id.rowTextView);
            lessonThumbnail = (ImageView) view.findViewById(R.id.lesson_thumbnail);
        }

        public void onClick(View view){
            String lessonName = lessonTextView.getText().toString();

            Course course = SessionData.getCurrentCourse();
            SessionData.setCurrentLesson(course.getLesson(lessonName));

            Context context = view.getContext();
            context.startActivity(new Intent(context, LessonActivity.class));
        }

    }
}
