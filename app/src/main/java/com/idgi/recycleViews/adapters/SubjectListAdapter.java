package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Subject;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    private ArrayList<String> subjectNames;
    private LayoutInflater inflater;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public SubjectListAdapter(Context context, ArrayList<String> subjectNames){
        this.subjectNames = subjectNames;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		//Pass on the PropertyChangeSupport object for communication between ViewHolder other classes
		viewHolder.pcs = this.pcs;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subjectTextView.setText(subjectNames.get(position));
    }

    @Override
    public int getItemCount() {
        return subjectNames.size();
    }

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView subjectTextView;
		public PropertyChangeSupport pcs;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            subjectTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String subjectName = subjectTextView.getText().toString();
            Subject subject = SessionData.getCurrentSchool().getSubject(subjectName);
            SessionData.setCurrentSubject(subject);

			pcs.firePropertyChange("startCourseListActivity", null, null);
        }

    }


}
