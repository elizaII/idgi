package com.idgi.android.recyclerview.viewHolder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idgi.R;
import com.idgi.core.ModelUtility;
import com.idgi.core.Nameable;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.service.FireDatabase;

import java.util.Locale;
import java.util.Random;

/*
Used to display a School in a list.
 */
public class SchoolViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row;

	public TextView nameTextView;
	private Nameable nameable;

	public SchoolViewHolder(View view){
		super(view);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent nameableEvent = new BusEvent(Event.SCHOOL_SELECTED, nameable);
			postToBus(nameableEvent);
		}
	};

	@Override
	public void bind(Nameable nameable) {
		this.nameable = nameable;
		nameTextView.setText(nameable.getName());
		randomizeBackground();
	}

	private void randomizeBackground() {
		TextView background = (TextView) findViewById(R.id.list_row_background);
		background.setBackgroundColor(randomColor());
	}

	private int randomColor() {
		Random color = new Random();
		int red = color.nextInt(256);
		int green = color.nextInt(256);
		int blue = color.nextInt(256);

		return Color.rgb(red,green,blue);
	}

	@Override
	public void initialize() {
		nameTextView = (TextView) findViewById(R.id.row_text_view);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new SchoolViewHolder(view);
	}
}