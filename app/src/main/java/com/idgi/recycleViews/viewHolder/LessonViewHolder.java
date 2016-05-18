package com.idgi.recycleViews.viewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;

public class LessonViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row;

	public TextView nameTextView;
	public RelativeLayout listBackground;

	public LessonViewHolder(View view, NameableSelectionBus bus){
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
	public void bind(Nameable nameable) {
		nameTextView.setText(nameable.getName());
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.rowTextView);
		listBackground = (RelativeLayout) findViewById(R.id.list_background);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent, NameableSelectionBus bus) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new LessonViewHolder(view, bus);
	}
}