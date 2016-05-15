package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.recycleViews.viewHolders.HatViewHolder;
import com.idgi.core.Hat;

import java.util.ArrayList;

/**
 * Created by tove on 2016-05-13.
 */
public class HatListAdapter extends RecyclerView.Adapter<HatViewHolder> {

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
}