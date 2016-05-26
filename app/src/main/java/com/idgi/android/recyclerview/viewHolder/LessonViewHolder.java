package com.idgi.android.recyclerview.viewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.idgi.Config;
import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

/*
Used to display a Lesson in a list.
 */
public class LessonViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row_lesson;

	public TextView nameTextView;
	public YouTubeThumbnailView lessonThumbnail;
	private Nameable nameable;

	public LessonViewHolder(View view) {
		super(view);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent nameableEvent = new BusEvent(Event.LESSON_SELECTED, nameable);
			postToBus(nameableEvent);
		}
	};

	@Override
	public void bind(final Nameable nameable) {
		this.nameable = nameable;
		nameTextView.setText(nameable.getName());
		lessonThumbnail.initialize(Config.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
			public void onInitializationSuccess(YouTubeThumbnailView view, final YouTubeThumbnailLoader loader) {
				Lesson lesson = (Lesson) nameable;
				loader.setVideo(lesson.getVideo().getUrl());
				loader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
					@Override
					public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
						loader.release();
					}

					@Override
					public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

					}
				});
			}

			public void onInitializationFailure(YouTubeThumbnailView view, YouTubeInitializationResult result) {
				Log.d("thumbnail", "Initialization didn't work");
			}
		});
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.row_text_view);
		lessonThumbnail = (YouTubeThumbnailView) findViewById(R.id.lesson_thumbnail);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new LessonViewHolder(view);
	}
}
