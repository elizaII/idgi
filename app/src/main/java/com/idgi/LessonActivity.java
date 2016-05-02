package com.idgi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.idgi.core.Lesson;
import com.idgi.core.Quiz;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;


public class LessonActivity extends AppCompatActivityWithDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Kvadratrötter och potenser");


        initializeDrawer();
    }

    public void onToQuizClick(View view) {
        /*
        //Retrieving current lesson and its quiz
        Lesson currentLesson = Storage.getCurrentLesson();
        Quiz quiz = currentLesson.getQuiz();

        //Setting it to the current quiz to be displayed in QuizActivity
        Storage.setCurrentQuiz(quiz);
        */
        startActivity(new Intent(this, QuizActivity.class));
    }
}
