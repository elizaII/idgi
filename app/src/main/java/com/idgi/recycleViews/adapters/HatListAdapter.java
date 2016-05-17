package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Hat;

import java.util.ArrayList;
import java.util.Locale;

public class HatListAdapter extends RecyclerView.Adapter<HatListAdapter.HatViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Hat> hats;

    public HatListAdapter(Context context, ArrayList<Hat> hats) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.hats = hats;
    }

    @Override
    public HatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_hat, parent, false);
        return new HatViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(HatViewHolder holder, int position) {
        holder.setUpView(hats.get(position));
    }

    @Override
    public int getItemCount() {
        return hats.size();
    }


    public static class HatViewHolder extends ChildViewHolder {

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
            String pointText = context.getResources().getString(R.string.hat_points);
            pointsView.setText(String.format(Locale.ENGLISH, pointText, hat.getPoints()));
            descriptionView.setText(hat.getDescription());
        }
    }
}