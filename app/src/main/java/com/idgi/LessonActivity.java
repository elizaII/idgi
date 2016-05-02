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

    Lesson currentLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(Storage.getCurrentLesson() != null) {
            currentLesson = Storage.getCurrentLesson();
            toolbar.setTitle(currentLesson.getName());
        } else {
            toolbar.setTitle("Kvadratrötter och potenser");
        }

        initializeDrawer();
    }

    public void onToQuizClick(View view) {
        /*
        //Retrieving the lesson's quiz
        Quiz quiz = currentLesson.getQuiz();

        //Setting it to the current quiz to be displayed in QuizActivity
        Storage.setCurrentQuiz(quiz);
        */

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("quiz_key", "Quiz123"); //Todo... replace with getCurrentQuiz()
        startActivity(intent);
    }
}
