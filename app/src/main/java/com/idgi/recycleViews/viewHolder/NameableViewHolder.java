package com.idgi.recycleViews.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;

public abstract class NameableViewHolder extends RecyclerView.ViewHolder {
	private NameableSelectionBus bus;
	private View rootView;

	public NameableViewHolder(View view, NameableSelectionBus bus){
		super(view);
		this.rootView = view;
		this.bus = bus != null ? bus : new NameableSelectionBus();

		initialize();
	}

	public void broadcastSelection(String name) {
		bus.broadcastSelection(name);
	}

	protected View findViewById(int id) {
		return rootView.findViewById(id);
	}

	protected static View getLayout(LayoutInflater inflater, ViewGroup parent, int viewType) {
		return inflater.inflate(viewType, parent, false);
	}

	public void applyRowStriping(int position) {
		boolean even = position % 2 == 0;
		int resource = even ?  R.color.white: R.color.colorPrimaryDark;

		rootView.setBackgroundResource(resource);
	}

	/** Initialize any views here. This is called when the ViewHolder is created. */
	public abstract void initialize();

	public abstract void bind(Nameable nameable);
}
