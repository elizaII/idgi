package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.idgi.R;
import com.idgi.util.ActivityType;
import com.idgi.core.Lesson;
import com.idgi.session.SessionData;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.ViewHolder> {
    private List<Lesson> lessons;
    private LayoutInflater inflater;

    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    private final String API_KEY = "AIzaSyDXWFAcvcUOV8JouYRStesIJgBu0eKRe-A";

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public LessonListAdapter(Context context, List<Lesson> lessons){
        this.lessons = lessons;
        inflater = LayoutInflater.from(context);

        ArrayList<String> lessonNames = new ArrayList<>();

        for(Lesson lesson : lessons)
            lessonNames.add(lesson.getName());

        Collections.sort(lessonNames);
    }

    @Override
    public LessonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_lesson, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		//Pass on the PropertyChangeSupport object for communication between ViewHolder other classes
		viewHolder.pcs = pcs;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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

        holder.lessonTextView.setText(lessonNames.get(position));
    }

    @Override
    public int getItemCount() {
        return lessonNames.size();
    }

	public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView lessonTextView;
        public YouTubeThumbnailView lessonThumbnail;
		public PropertyChangeSupport pcs;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            lessonTextView = (TextView) view.findViewById(R.id.rowTextView);
            lessonThumbnail = (YouTubeThumbnailView) view.findViewById(R.id.lesson_thumbnail);
        }


        public void onClick(View view){
            String lessonName = lessonTextView.getText().toString();

            Lesson lesson = SessionData.getCurrentCourse().getLesson(lessonName);
            SessionData.setCurrentLesson(lesson);

			pcs.firePropertyChange("startActivity", null, ActivityType.LESSON);
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
