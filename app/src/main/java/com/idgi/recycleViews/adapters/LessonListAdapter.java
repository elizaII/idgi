package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.idgi.Config;
import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.event.NameableSelectionBus;
import java.util.List;

public class LessonListAdapter extends NameableAdapter<LessonListAdapter.ViewHolder>  {

    public LessonListAdapter(Context context, List<Lesson> lessons){
        super(context, lessons);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.list_row_lesson, parent, false);
        return new ViewHolder(view, getBus());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.lessonTextView.setText(nameables.get(position).getName());

        holder.lessonThumbnail.initialize(Config.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
                int position = holder.getAdapterPosition();
                Lesson lesson = (Lesson) nameables.get(position);
                loader.setVideo(lesson.getVideo().getUrl());
                loader.release();
                Log.d("thumbnail", "Initialization worked");
            }

            public void onInitializationFailure(YouTubeThumbnailView view, YouTubeInitializationResult result) {
                Log.d("thumbnail", "Initialization didn't work");
            }
        });
    }

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener{

        public TextView lessonTextView;
        public YouTubeThumbnailView lessonThumbnail;

        public ViewHolder(View view, NameableSelectionBus bus){
            super(view, bus);
            view.setOnClickListener(this);
            lessonTextView = (TextView) view.findViewById(R.id.rowTextView);
            lessonThumbnail = (YouTubeThumbnailView) view.findViewById(R.id.lesson_thumbnail);
        }

        public void onClick(View view){
            String lessonName = lessonTextView.getText().toString();
            broadcastSelection(lessonName);
        }
    }
}
