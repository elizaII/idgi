package com.idgi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.idgi.R;
import com.idgi.Widgets.CreateDialog;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.School;
import com.idgi.core.Video;
import com.idgi.services.FireDatabase;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class CreateLessonActivity extends AppCompatActivity {

    private FireDatabase database = FireDatabase.getInstance();
    private EditText lesson_name_editText;
    private EditText youtube_url_editText;
    private ArrayList<String> schoolNames;
    private ArrayList<String> subjectNames;
    private ArrayList<String> courseNames;
    private Button add_school_button;
    private Button add_subject_button;
    private Button add_course_button;
    private Button add_quiz_button;

    private boolean hasQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);






        lesson_name_editText = (EditText) findViewById(R.id.lesson_name_editText);
        youtube_url_editText = (EditText) findViewById(R.id.youtube_url_editText);
        add_school_button = (Button) findViewById(R.id.add_school_button);
        add_subject_button = (Button) findViewById(R.id.add_subject_button);
        add_course_button = (Button) findViewById(R.id.add_course_button);
        add_quiz_button = (Button) findViewById(R.id.add_quiz_button);

        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.getString("quiz").equals("quiz")) {
            add_school_button.setText(Storage.getCurrentSchool().getName());
            add_subject_button.setText(Storage.getCurrentSubject().getName());
            add_course_button.setText(Storage.getCurrentCourse().getName());
            add_quiz_button.setText("Quiz is added.");

            System.out.println(Storage.getCurrentQuiz().getQuestions().size());

            hasQuiz=true;
        }

        initiateLists();
    }

    public void initiateLists() {

        schoolNames = new ArrayList<String>();
        for (School school : database.getSchools()){
            schoolNames.add(school.getName());
            System.out.println(school.getName());
        }


        if(!add_school_button.getText().toString().equals("Lägg till skola")){
            System.out.println("Letar ämnen");
        subjectNames = new ArrayList<String>();
        for (com.idgi.core.Subject subject : database.getSubjects(add_school_button.getText().toString())){
            subjectNames.add(subject.getName());
        }
        }


        if(!add_subject_button.getText().toString().equals("Lägg till ämne")){
            System.out.println("Letar kurser");
        courseNames = new ArrayList<String>();
        for (Course course : database.getCourses(add_school_button.getText().toString(), add_subject_button.getText().toString())){
            courseNames.add(course.getName());
        }
        }


        }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_school_button:
                CreateDialog dialog=new CreateDialog(this, "skola", "skola", "skola,", schoolNames);
                dialog.show();
                break;
            case R.id.add_subject_button:
                CreateDialog dialog1=new CreateDialog(this, "ämne", "ämne", add_school_button.getText().toString(),subjectNames);
                dialog1.show();
                break;
            case R.id.add_course_button:
                CreateDialog dialog2=new CreateDialog(this, "kurs", add_school_button.getText().toString(), add_subject_button.getText().toString(), courseNames);
                System.out.println("Kursknapppppppppp");
                dialog2.show();
                break;
            default:
                break;
        }

    }

    public void selectItem(String text, String string) {
        if (string.equals("skola")) {
            Storage.setCurrentSchool(database.getSchool(text));
            add_school_button.setText(text);
            initiateLists();
        }
        if (string.equals("ämne")) {
            Storage.setCurrentSubject(Storage.getCurrentSchool().getSubject(text));
            add_subject_button.setText(text);
            initiateLists();
        }
        if (string.equals("kurs")) {
            Storage.setCurrentCourse(Storage.getCurrentSubject().getCourse(text));
            add_course_button.setText(text);
            initiateLists();
        }
    }

    public void onAddQuizButtonClick(View view) {
        Intent intent = new Intent(CreateLessonActivity.this, CreateQuizActivity.class);
        startActivity(intent);

    }

    public void onCreateLessonButtonClick(View view){

        Lesson lesson = new Lesson(lesson_name_editText.getText().toString(), new Video(youtube_url_editText.getText().toString()));
        if (hasQuiz){
            lesson.setQuiz(Storage.getCurrentQuiz());
        }
        Storage.getCurrentCourse().addLesson(lesson);
        Storage.setCurrentLesson(lesson);
        startActivity(new Intent(CreateLessonActivity.this, LessonActivity.class));
    }
}
