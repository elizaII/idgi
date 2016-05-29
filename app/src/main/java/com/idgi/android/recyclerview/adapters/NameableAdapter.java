package com.idgi.android.recyclerview.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.idgi.core.Nameable;
import com.idgi.android.recyclerview.viewHolder.NameableViewHolder;
import com.idgi.android.recyclerview.viewHolder.ViewHolderFactory;
import com.idgi.util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
A general adapter used by every list that displays Nameables.
 */
public class NameableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<? extends Nameable> nameables;
    private LayoutInflater inflater;

    public NameableAdapter(Context context, List<? extends Nameable> nameables){
        Collections.sort(nameables, Util.SORT_BY_NAME);

        this.nameables = nameables;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Nameable nameable = nameables.get(position);
        return nameable.getType().getValue();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.createNameableViewHolder(inflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NameableViewHolder nameableViewHolder = (NameableViewHolder) viewHolder;
        nameableViewHolder.bind(nameables.get(position));
    }

    @Override
    public int getItemCount() {
        return nameables.size();
    }
}