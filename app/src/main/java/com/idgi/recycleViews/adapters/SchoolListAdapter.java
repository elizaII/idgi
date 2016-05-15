package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.SubjectListActivity;
import com.idgi.core.School;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.ActivityType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {

    private ArrayList<String> schoolNames;
    private LayoutInflater inflater;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public SchoolListAdapter(Context context, ArrayList<String> schoolNames){
        this.schoolNames = schoolNames;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		//Pass on the PropertyChangeSupport object for communication between ViewHolder other classes
		viewHolder.pcs = pcs;

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.schoolTextView.setText(schoolNames.get(position));
    }

    @Override
    public int getItemCount() {
        return schoolNames.size();
    }

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView schoolTextView;
		public PropertyChangeSupport pcs;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            schoolTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String schoolName = schoolTextView.getText().toString();
            School school = FireDatabase.getInstance().getSchool((schoolName));

            SessionData.setCurrentSchool(school);

			pcs.firePropertyChange("startActivity", null, ActivityType.SUBJECT_LIST);
        }

    }


}
