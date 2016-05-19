package com.idgi.recycleViews.viewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;

public class SchoolViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row;

	public TextView nameTextView;
	public RelativeLayout listBackground;
	private Nameable nameable;

	public SchoolViewHolder(View view, NameableSelectionBus bus){
		super(view, bus);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			broadcastSelection(nameable);
		}
	};

	@Override
	public void bind(Nameable nameable) {
		this.nameable = nameable;
		nameTextView.setText(nameable.getName());
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.rowTextView);
		listBackground = (RelativeLayout) findViewById(R.id.list_background);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent, NameableSelectionBus bus) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new SchoolViewHolder(view, bus);
	}
}