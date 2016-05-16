package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.event.NameableSelectionBus;
import java.util.List;



public class CourseListAdapter extends NameableAdapter<CourseListAdapter.ViewHolder> {

    public CourseListAdapter(Context context, List<Course> courses) {
        super(context, courses);
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

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener {

        public TextView schoolTextView;

        public ViewHolder(View view, NameableSelectionBus bus) {
            super(view, bus);
            view.setOnClickListener(this);
            schoolTextView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view) {
            String schoolName = schoolTextView.getText().toString();
            broadcastSelection(schoolName);
        }
    }

}
