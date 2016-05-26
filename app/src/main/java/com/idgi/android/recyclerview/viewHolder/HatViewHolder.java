package com.idgi.android.recyclerview.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.idgi.R;
import com.idgi.core.Hat;
import com.idgi.core.Nameable;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

import java.util.Locale;

/*
Used to display a Hat in a list.
 */
public class HatViewHolder extends NameableViewHolder {

	public static final int LAYOUT = R.layout.list_row_hat;

	private ImageView imageView;
	private TextView nameView;
	private TextView pointsView;
	private TextView descriptionView;
	private Hat hat;

	public HatViewHolder(View view) {
		super(view);
		view.setOnClickListener(onViewClick);
	}

	private final View.OnClickListener onViewClick = new View.OnClickListener() {
		public void onClick(View view) {
			BusEvent event = new BusEvent(Event.HAT_SELECTED, hat);
			postToBus(event);
		}
	};

	@Override
	public void bind(final Nameable nameable) {
		this.hat = (Hat) nameable;
		imageView.setImageResource(hat.getImageId());
		nameView.setText(hat.getName());
		String pointText = itemView.getContext().getResources().getString(R.string.hat_points);
		pointsView.setText(String.format(Locale.ENGLISH, pointText, hat.getPoints()));
		descriptionView.setText(hat.getDescription());
	}

	@Override
	public void initialize() {
		imageView = (ImageView) itemView.findViewById(R.id.hat_list_image);
		nameView = (TextView) itemView.findViewById(R.id.hat_list_name);
		pointsView = (TextView) itemView.findViewById(R.id.hat_list_points);
		descriptionView = (TextView) itemView.findViewById(R.id.hat_list_description);
	}

	public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
		View view = getLayout(inflater, parent, LAYOUT);
		return new HatViewHolder(view);
	}
}
