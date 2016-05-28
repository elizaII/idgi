package com.idgi.android.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Teacher;
import com.idgi.core.User;
import com.idgi.session.SessionData;

import java.util.Locale;

public class StartLoggedInFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {


           View view = inflater.inflate(
                   R.layout.fragment_start_logged_in, container, false);

            initialize(view);

            return view;
        }

    private void initialize(View view) {
        initializeWelcomeMessage(view);

        TextView logo = (TextView) view.findViewById(R.id.start_title);
        TextView welcomeTxt = (TextView) view.findViewById(R.id.start_txt_welcome);
        Button findCourses = (Button) view.findViewById(R.id.start_btn_browse);

        //animateViewSequentially(logo, welcomeTxt, findCourses);

        if (SessionData.getLoggedInUser() instanceof Teacher)
            initializeForTeacher(view);
    }

    private void initializeWelcomeMessage(View view) {
        TextView welcomeText = (TextView) view.findViewById(R.id.start_txt_welcome);
        User user = SessionData.getLoggedInUser();

        if (user != null) {
            String welcomeMsg = String.format(Locale.ENGLISH,
                    getResources().getString(R.string.start_txt_welcome_msg),
                    user.getName());

            if (welcomeText != null)
                welcomeText.setText(welcomeMsg);
        }
    }

    private void initializeForTeacher(View view){
        FloatingActionButton createLessonButton = (FloatingActionButton)
                view.findViewById(R.id.start_fab_create_lesson);

        if(createLessonButton != null) {
            createLessonButton.setVisibility(View.VISIBLE);
        }
    }

    private void animateViewSequentially(View... views) {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_top);
        int offset = 0;

        for(View view : views) {
            if(view != null) {
                fadeIn.setStartOffset(offset);
                view.startAnimation(fadeIn);
                offset += 40;
            }
        }
    }

}
