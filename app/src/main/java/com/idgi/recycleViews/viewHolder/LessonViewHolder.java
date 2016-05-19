package com.idgi.recycleViews.viewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.idgi.Config;
import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;

public class LessonViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row_lesson;

	public TextView nameTextView;
	public YouTubeThumbnailView lessonThumbnail;
	public RelativeLayout listBackground;

	public LessonViewHolder(View view, NameableSelectionBus bus) {
		super(view, bus);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			String lessonName = nameTextView.getText().toString();
			broadcastSelection(lessonName);
		}
	};

	@Override
	public void bind(final Nameable nameable) {
		nameTextView.setText(nameable.getName());
		lessonThumbnail.initialize(Config.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
			public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
				Lesson lesson = (Lesson) nameable;
				loader.setVideo(lesson.getVideo().getUrl());
			}

			public void onInitializationFailure(YouTubeThumbnailView view, YouTubeInitializationResult result) {
				Log.d("thumbnail", "Initialization didn't work");
			}
		});
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.rowTextView);
		lessonThumbnail = (YouTubeThumbnailView) findViewById(R.id.lesson_thumbnail);
		listBackground = (RelativeLayout) findViewById(R.id.list_background);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent, NameableSelectionBus bus) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new LessonViewHolder(view, bus);
	}
}
