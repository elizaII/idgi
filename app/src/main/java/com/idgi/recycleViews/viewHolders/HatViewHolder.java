package com.idgi.recycleViews.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Hat;

/**
 * Created by tove on 2016-05-13.
 */
public class HatViewHolder extends ChildViewHolder {

    private Context context;

    private ImageView imageView;
    private TextView nameView;
    private TextView pointsView;
    private TextView descriptionView;

    public HatViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;

        imageView = (ImageView) itemView.findViewById(R.id.hat_list_image);
        nameView = (TextView) itemView.findViewById(R.id.hat_list_name);
        pointsView = (TextView) itemView.findViewById(R.id.hat_list_points);
        descriptionView = (TextView) itemView.findViewById(R.id.hat_list_description);
    }

    public void setUpView(Hat hat) {
        imageView.setImageResource(hat.getImageId());
        nameView.setText(hat.getName());
        pointsView.setText(hat.getPoints() + " " + context.getResources().getString(R.string.hat_points));
        descriptionView.setText(hat.getDescription());
    }
}
