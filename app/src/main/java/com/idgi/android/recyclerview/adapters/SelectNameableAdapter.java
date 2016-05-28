package com.idgi.android.recyclerview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import java.util.List;

public class SelectNameableAdapter extends RecyclerView.Adapter<SelectNameableAdapter.ViewHolder> {

    private List<? extends Nameable> nameables;
    private LayoutInflater inflater;

    public SelectNameableAdapter(Context context, List<? extends Nameable> nameables){
        this.nameables = nameables;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameable = nameables.get(position);
        holder.textView.setText(holder.nameable.getName());
    }

    @Override
    public int getItemCount() {
        return nameables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public Nameable nameable;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            textView = (TextView) view.findViewById(R.id.row_text_view);
        }

        public void onClick(View view){
            BusEvent event = new BusEvent(Event.NAMEABLE_SELECTED, nameable);
            ApplicationBus.post(event);
        }
    }
}