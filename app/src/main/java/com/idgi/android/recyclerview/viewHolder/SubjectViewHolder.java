package com.idgi.android.recyclerview.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

/*
Used to display a Subject in a list.
 */
public class SubjectViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row;

	public TextView nameTextView;
	private Nameable nameable;

	public SubjectViewHolder(View view){
		super(view);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent nameableEvent = new BusEvent(Event.SUBJECT_SELECTED, nameable);
			ApplicationBus.post(nameableEvent);
		}
	};

	@Override
	public void bind(Nameable nameable) {
		this.nameable = nameable;
		nameTextView.setText(nameable.getName());
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.row_text_view);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new SubjectViewHolder(view);
	}
}