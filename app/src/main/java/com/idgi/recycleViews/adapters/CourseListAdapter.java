package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.recycleViews.viewHolders.CourseHolder;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseHolder> {

    private ArrayList<Course> itemList;
    private LayoutInflater inflater;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CourseListAdapter(Context context, ArrayList<Course> itemList){
        this.itemList = new ArrayList<>();
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.course_list_listitem_course, parent, false);
        return new CourseHolder(view, this);
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        for (PropertyChangeListener listener : pcs.getPropertyChangeListeners())
            holder.addPropertyChangeListener(listener);

        holder.courseTextView.setText(itemList.get(position).getName());
        if (SessionData.hasLoggedInUser()) {
            if (SessionData.getLoggedInUser().getMyCourses().contains(itemList.get(position))) {
                holder.btnMyCourses.setText(R.string.added_to_my_courses);
            }
        }
        else {
            holder.btnMyCourses.setText(R.string.log_in_message);
            holder.btnMyCourses.setEnabled(false);
        }
/*        if(itemList.get(position).getDescription().length()>0){
            holder.courseDescriptionTextView.setText(itemList.get(position).getDescription());
        }else{*/
            holder.courseDescriptionTextView.setText(R.string.no_description);
  //      }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
}
