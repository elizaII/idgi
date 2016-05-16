package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.core.User;
import com.idgi.session.SessionData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Locale;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseHolder> {

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

    public static class CourseHolder extends RecyclerView.ViewHolder {
        public TextView courseTextView;
        public Button btnMyCourses;
        private CourseListAdapter adapter;
        public TextView courseInformationTextView;
        public TextView courseDescriptionTextView;

        private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


        public CourseHolder(View view, CourseListAdapter adapter){
            super(view);
            view.setOnClickListener(onListItemClick);
            this.adapter = adapter;
            courseTextView = (TextView) view.findViewById(R.id.course_list_listitem_course_name);
            courseDescriptionTextView = (TextView) view.findViewById(R.id.courseDescriptionTextView);
            courseInformationTextView = (TextView) view.findViewById(R.id.courseInformationTextView);
            btnMyCourses = (Button) view.findViewById(R.id.course_list_listitem_course_my_courses_button);
            btnMyCourses.setOnClickListener(onMyCoursesClick);
            courseTextView.setOnClickListener(onListItemClick);
        }

        private final View.OnClickListener onListItemClick = new View.OnClickListener() {
            public void onClick(View view) {
                String courseName = courseTextView.getText().toString();
                Course course = SessionData.getCurrentSubject().getCourse(courseName);
                if (course == null)
                    throw new IllegalStateException(String.format(Locale.ENGLISH,
                            "Course with name %s should be in current subject, but isn't.", courseName));
                else {
                    SessionData.setCurrentCourse(course);
                    pcs.firePropertyChange("startCourseActivity", null, null);
                }
            }
        };

        private final View.OnClickListener onMyCoursesClick = new View.OnClickListener() {
            public void onClick(View view) {
                String courseName = courseTextView.getText().toString();
                Course course = SessionData.getCurrentSubject().getCourse(courseName);

                if (SessionData.hasLoggedInUser()) {
                    User user = SessionData.getLoggedInUser();

                    if (user.isInMyCourses(course)) {
                        user.removeFromMyCourses(course);
                        btnMyCourses.setText(R.string.add_to_my_courses);
                        adapter.notifyDataSetChanged();
                    } else {
                        user.addToMyCourses(course);
                        btnMyCourses.setText(R.string.added_to_my_courses);
                    }
                }
            }
        };

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            this.pcs.addPropertyChangeListener(listener);
        }
    }

}
