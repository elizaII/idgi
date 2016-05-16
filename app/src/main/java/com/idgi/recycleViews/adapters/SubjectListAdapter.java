package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Subject;
import com.idgi.event.NameableSelectionBus;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class SubjectListAdapter extends NameableAdapter<SubjectListAdapter.ViewHolder> {

    public SubjectListAdapter(Context context, List<Subject> subjects){
        super(context, subjects);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, getBus());

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subjectTextView.setText(nameables.get(position).getName());
    }


    public void addListener(NameableSelectionBus.Listener listener) {
        getBus().addListener(listener);
    }

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener{

        public TextView subjectTextView;

        public ViewHolder(View view, NameableSelectionBus bus){
            super(view, bus);
            view.setOnClickListener(this);
            subjectTextView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String subjectName = subjectTextView.getText().toString();
            broadcastSelection(subjectName);
        }
    }

}
