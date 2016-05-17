package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.event.NameableSelectionBus;
import com.idgi.session.SessionData;

import java.util.List;



public class CourseListAdapter extends NameableAdapter<CourseListAdapter.ViewHolder> {

    public CourseListAdapter(Context context, List<Course> courses) {
        super(context, courses);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.course_list_listitem_course, parent, false);
        return new ViewHolder(view, getBus(), this);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = (Course)nameables.get(position);
        holder.courseTextView.setText(course.getName());
        if (course.getDescription().length()>0) {
            holder.courseDescriptionTextView.setText(course.getDescription());
        }
        else{
            holder.courseDescriptionTextView.setText(R.string.no_description);
        }
        holder.courseInformationTextView.setText(course.getParentSchool() + " > " + course.getParentSubject());
        if(position % 2 != 0)
        {
            holder.listBackground.setBackgroundResource(R.color.colorPrimaryDark);
        }
        else {
            holder.listBackground.setBackgroundResource(R.color.white);
        }
        if (SessionData.hasLoggedInUser()) {
            if (SessionData.getLoggedInUser().getMyCourses().contains(nameables.get(position))){
                holder.isAddedToMyCourses=true;
                holder.myCoursesButton.setText(R.string.added_to_my_courses);
            }
        }
        else {
            holder.myCoursesButton.setText(R.string.log_in_message);
            holder.myCoursesButton.setEnabled(false);
        }
    }

    public void addListener(NameableSelectionBus.Listener listener) {
        getBus().addListener(listener);
    }

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener {

        public TextView courseTextView;
        public TextView courseDescriptionTextView;
        public TextView courseInformationTextView;
        public RelativeLayout listBackground;
        public Button myCoursesButton;
        public boolean isAddedToMyCourses = false;
        public CourseListAdapter adapter;

        public ViewHolder(View view, NameableSelectionBus bus,  CourseListAdapter adapter) {
            super(view, bus);
            view.setOnClickListener(this);
            courseTextView = (TextView) view.findViewById(R.id.rowTextView);
            courseInformationTextView = (TextView) view.findViewById(R.id.courseInformationTextView);
            courseDescriptionTextView = (TextView) view.findViewById(R.id.courseDescriptionTextView);
            listBackground = (RelativeLayout) view.findViewById(R.id.list_background);
            myCoursesButton = (Button) view.findViewById(R.id.course_list_listitem_course_my_courses_button);
            myCoursesButton.setOnClickListener(this);
            this.adapter=adapter;
        }

        public void onClick(View view){
            String courseName = courseTextView.getText().toString();
            Course course = SessionData.getCurrentSubject().getCourse(courseName);
            switch (view.getId()) {
                case R.id.course_list_listitem_course_my_courses_button:
                    if (SessionData.hasLoggedInUser()) {
                        if (isAddedToMyCourses) {
                            SessionData.getLoggedInUser().removeFromMyCourses(course);
                            myCoursesButton.setText(R.string.add_to_my_courses);
                            isAddedToMyCourses = false;
                            adapter.notifyDataSetChanged();
                        } else {
                           SessionData.getLoggedInUser().addToMyCourses(course);
                            myCoursesButton.setText(R.string.added_to_my_courses);
                            isAddedToMyCourses = true;
                        }
                    }
                    break;
                default:
                    broadcastSelection(courseName);
                    break;
            }
        }
    }

}
