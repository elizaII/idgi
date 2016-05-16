package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.School;
import com.idgi.event.NameableSelectionBus;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class SchoolListAdapter extends NameableAdapter<SchoolListAdapter.ViewHolder> {

    public SchoolListAdapter(Context context, List<School> schools){
        super(context, schools);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, getBus());

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.schoolTextView.setText(nameables.get(position).getName());
    }

    public void addListener(NameableSelectionBus.Listener listener) {
        getBus().addListener(listener);
    }

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener{

        public TextView schoolTextView;

        public ViewHolder(View view, NameableSelectionBus bus){
            super(view, bus);
            view.setOnClickListener(this);
            schoolTextView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String schoolName = schoolTextView.getText().toString();
            broadcastSelection(schoolName);
        }
    }
}
