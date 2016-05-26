package com.idgi.android.recyclerview.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;

/*
Used to display a User in a list.
 */
public class UserViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row;

	public TextView nameTextView;
	private Nameable nameable;

	public UserViewHolder(View view){
		super(view);
		view.setOnClickListener(onViewClick);
	}


	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			// TODO Go to a user's profile if the user is clicked.
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
		return new UserViewHolder(view);
	}
}