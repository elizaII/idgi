package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.activities.CourseActivity;
import com.idgi.R;
import com.idgi.core.Course;
import com.idgi.session.SessionData;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private ArrayList<Course> data;
    private LayoutInflater inflater;


    public CourseListAdapter(Context context, ArrayList<Course> data){
        this.data = new ArrayList<>();
        this.data=data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_course, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.courseTextView.setText(data.get(position).getName());
        if (SessionData.hasLoggedInUser()) {
            if (SessionData.getLoggedInUser().getMyCourses().contains(data.get(position))){
                holder.isAddedToMyCourses=true;
                holder.my_courses_button.setText(R.string.added_to_my_courses);
            }
        }
        else {
            holder.my_courses_button.setText(R.string.log_in_message);
            holder.my_courses_button.setEnabled(false);
        }
/*        if(data.get(position).getDescription().length()>0){
            holder.courseDescriptionTextView.setText(data.get(position).getDescription());
        }else{*/
            holder.courseDescriptionTextView.setText(R.string.no_description);
  //      }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView courseTextView;
        public Button my_courses_button;
        protected boolean isAddedToMyCourses = false;
        private CourseListAdapter adapter;
        public TextView courseInformationTextView;
        public TextView courseDescriptionTextView;


        public ViewHolder(View v, CourseListAdapter adapter){
            super(v);
            v.setOnClickListener(this);
            this.adapter=adapter;
            courseTextView =(TextView) v.findViewById(R.id.rowTextView);
            courseDescriptionTextView =(TextView) v.findViewById(R.id.courseDescriptionTextView);
            courseInformationTextView =(TextView) v.findViewById(R.id.courseInformationTextView);
            my_courses_button = (Button) v.findViewById(R.id.my_courses_button);
            my_courses_button.setOnClickListener(this);
            courseTextView.setOnClickListener(this);
        }

        public void onClick(View view){
            String courseName = courseTextView.getText().toString();
            Course course = SessionData.getCurrentSubject().getCourse(courseName);
            switch (view.getId()) {
                case R.id.rowTextView:
                    SessionData.setCurrentCourse(course);
                    Context context = view.getContext();
                    context.startActivity(new Intent(context, CourseActivity.class));
                    break;
                case R.id.my_courses_button:
                    if (SessionData.hasLoggedInUser()) {
                        if (isAddedToMyCourses) {
                            SessionData.getLoggedInUser().removeFromMyCourses(course);
                            my_courses_button.setText(R.string.add_to_my_courses);
                            isAddedToMyCourses = false;
                            adapter.notifyDataSetChanged();
                        } else {
                            SessionData.getLoggedInUser().addToMyCourses(course);
                            my_courses_button.setText(R.string.added_to_my_courses);
                            isAddedToMyCourses = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
