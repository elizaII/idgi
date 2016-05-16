package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.EventBus;
import com.idgi.event.NameableSelectionBus;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class NameableAdapter<ExternalViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ExternalViewHolder> {
    protected List<? extends Nameable> nameables;
    private LayoutInflater inflater;

	private NameableSelectionBus bus = new NameableSelectionBus();

    private final Comparator<Nameable> SORT_BY_NAME = new Comparator<Nameable>() {
        public int compare(Nameable first, Nameable second) {
            return first.getName().compareTo(second.getName());
        }
    };

    public NameableAdapter(Context context, List<? extends Nameable> nameables){
        inflater = LayoutInflater.from(context);

		Collections.sort(nameables, SORT_BY_NAME);
        this.nameables = nameables;
    }

    @Override
    public int getItemCount() {
        return nameables.size();
    }

	public void addListener(NameableSelectionBus.Listener listener) {
		bus.addListener(listener);
	}

	/** Should only be called if necessary. Creates a bus on its own in normal circumstances. */
	public void setBus(NameableSelectionBus bus) {
		this.bus = bus;
	}

	public NameableSelectionBus getBus() {
		return this.bus;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private NameableSelectionBus bus;

		public ViewHolder(View view, NameableSelectionBus bus){
			super(view);

			this.bus = bus;
		}

		public void broadcastSelection(String name) {
			bus.broadcastSelection(name);
		}
	}

	protected LayoutInflater getInflater() {
		return this.inflater;
	}
}
