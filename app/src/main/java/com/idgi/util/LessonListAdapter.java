package com.idgi.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.LessonActivity;
import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.services.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 01/05/2016.
 */
public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public LessonListAdapter(Context context, ArrayList<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public LessonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
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

        private Database db = Database.getInstance();
        private List<Lesson> lessons = db.getLessons();

        public TextView lessonTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            lessonTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View v){
            String s = lessonTextView.getText().toString();

            for(Lesson lesson : lessons){
                if(s.equals(lesson.getName())){
                    Storage.setCurrentLesson(lesson);
                    Storage.setCurrentVideo(lesson.getVideo());
                    Log.d("CURRENT_LESSON", "onClick: " + lesson.getName());
                }
            }

            Intent intent = new Intent(v.getContext(), LessonActivity.class);
            v.getContext().startActivity(intent);
        }

    }
}
