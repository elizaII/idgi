package com.idgi.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.session.SessionData;

/*
Presents info about the selected course.
 */
public class CourseInfoFragment extends Fragment {

    public CourseInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);
        initializeInfoView(view);

        return view;
    }

    private void initializeInfoView(View view){
       if(currentCourseHasDescription()) {
            TextView courseInfo = (TextView) view.findViewById(R.id.course_info_txt);
            courseInfo.setText(SessionData.getCurrentCourse().getDescription());
            View emptyView = view.findViewById(R.id.course_info_empty);
            if(emptyView != null)
                emptyView.setVisibility(View.INVISIBLE);
        } else {
            View infoCard = view.findViewById(R.id.fragment_course_info_card_info);
            if(infoCard != null)
                infoCard.setVisibility(View.INVISIBLE);
        }

    }

    private boolean currentCourseHasDescription(){
        return (SessionData.getCurrentCourse().getDescription() != null &&
                SessionData.getCurrentCourse().getDescription().length() > 0 &&
                !SessionData.getCurrentCourse().getDescription().equals(getResources().getString(R.string.course_no_info)));
    }
}
