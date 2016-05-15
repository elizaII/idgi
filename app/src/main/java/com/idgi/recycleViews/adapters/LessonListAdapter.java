package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.idgi.activities.LessonActivity;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {

    private List<Lesson> data;
    private LayoutInflater inflater;

    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    private final String API_KEY = "AIzaSyDXWFAcvcUOV8JouYRStesIJgBu0eKRe-A";


    public LessonListAdapter(Context context, List<Lesson> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

        ArrayList<String> lessonNames = new ArrayList<>();

        for(Lesson lesson : data)
            lessonNames.add(lesson.getName());

        Collections.sort(lessonNames);

    }

    @Override
    public LessonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_lesson, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonListAdapter.ViewHolder holder, final int position) {
        holder.lessonTextView.setText(data.get(position).getName());
        holder.lessonThumbnail.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(data.get(position).getVideo().getUrl());
                Log.d("thumbnail", "worked");

            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("thumbnail", "didn't work");

            }

    });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
/*
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView thumbnailView,
                                        YouTubeThumbnailLoader thumbnailLoader) {

        thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());
        thumbnailLoader.setVideo(data.get());

        Log.d("thumbnail", "worked");

    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("thumbnail", "didn't work");
    }
*/
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lessonTextView;
        public YouTubeThumbnailView lessonThumbnail;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            lessonTextView = (TextView) view.findViewById(R.id.rowTextView);
            lessonThumbnail = (YouTubeThumbnailView) view.findViewById(R.id.lesson_thumbnail);
        }


        public void onClick(View view){
            String lessonName = lessonTextView.getText().toString();

            Course course = SessionData.getCurrentCourse();
            SessionData.setCurrentLesson(course.getLesson(lessonName));

            Context context = view.getContext();
            context.startActivity(new Intent(context, LessonActivity.class));
        }

    }

    private final class ThumbnailLoadedListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailError(YouTubeThumbnailView arg0, YouTubeThumbnailLoader.ErrorReason arg1) {

        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView arg0, String arg1) {

        }

    }
}
