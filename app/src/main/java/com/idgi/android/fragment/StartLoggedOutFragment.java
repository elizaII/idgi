package com.idgi.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.session.SessionData;

public class StartLoggedOutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_start_logged_out, container, false);

        initialize(view);
        return view;
    }

    private void initialize(View view) {
        TextView logo = (TextView) view.findViewById(R.id.start_title);
        Button findCourses = (Button) view.findViewById(R.id.start_btn_browse);
        Button logIn = (Button) view.findViewById(R.id.start_btn_log_in);
        Button createAccount = (Button) view.findViewById(R.id.start_btn_create_account);

        if (SessionData.isFirstRun()) {
            animateViewSequentially(logo, findCourses, logIn, createAccount);
            SessionData.setFirstRun(false);
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
