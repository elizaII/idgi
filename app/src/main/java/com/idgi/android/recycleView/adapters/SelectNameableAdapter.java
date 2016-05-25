package com.idgi.android.recycleView.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import java.util.List;

public class SelectNameableAdapter extends RecyclerView.Adapter<SelectNameableAdapter.ViewHolder> {

    private List<String> itemNames;
    private LayoutInflater inflater;

    public SelectNameableAdapter(Context context, List<String> itemNames){
        this.itemNames = itemNames;
        inflater = LayoutInflater.from(context);
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(itemNames.get(position));
        }

    @Override
    public int getItemCount() {
        return itemNames.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            textView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String itemName = textView.getText().toString();
			BusEvent event = new BusEvent(Event.NAMEABLE_SELECTED, itemName);
            ApplicationBus.post(event);
        }
    }
}
