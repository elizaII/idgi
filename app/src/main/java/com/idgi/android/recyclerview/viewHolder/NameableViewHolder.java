package com.idgi.android.recyclerview.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.idgi.core.Nameable;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;

/*
Template class for displaying Nameables in lists.
 */
public abstract class NameableViewHolder extends RecyclerView.ViewHolder {
	private View rootView;

	public NameableViewHolder(View view){
		super(view);
		this.rootView = view;

		initialize();
	}

	protected View findViewById(int id) {
		return rootView.findViewById(id);
	}

	protected static View getLayout(LayoutInflater inflater, ViewGroup parent, int viewType) {
		return inflater.inflate(viewType, parent, false);
	}

	/** Initialize any views here. This is called when the ViewHolder is created. */
	public abstract void initialize();

	public abstract void bind(Nameable nameable);
}
