package com.idgi;

import android.os.Bundle;
import com.idgi.util.AppCompatActivityWithDrawer;


public class LessonActivity extends AppCompatActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initializeDrawer();
    }
}
