package com.idgi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.Widgets.CreateDialog;
import com.idgi.Widgets.CreateQuizDialog;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Video;
import com.idgi.services.FireDatabase;
import com.idgi.util.Storage;

import java.util.ArrayList;

public class CreateLessonActivity extends AppCompatActivity {

    private FireDatabase database = FireDatabase.getInstance();
    private EditText lesson_name_editText;
    private EditText youtube_url_editText;
    private ArrayList<String> schoolNames;
    private ArrayList<String> subjectNames;
    private ArrayList<String> courseNames;
    private ArrayList<Question> questionList;
    private Button add_school_button;
    private Button add_subject_button;
    private Button add_course_button;
    private Button add_quiz_button;
    private Button create_lesson_button;

    private boolean hasQuiz = false;
    private boolean newSchool = false;
    private boolean newSubject= false;
    private boolean newCourse = false;

    private School school;
    private com.idgi.core.Subject subject;
    private Course course;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        questionList=new ArrayList<Question>();

        lesson_name_editText = (EditText) findViewById(R.id.lesson_name_editText);
        youtube_url_editText = (EditText) findViewById(R.id.youtube_url_editText);
        add_school_button = (Button) findViewById(R.id.add_school_button);
        add_subject_button = (Button) findViewById(R.id.add_subject_button);
        add_course_button = (Button) findViewById(R.id.add_course_button);
        add_quiz_button = (Button) findViewById(R.id.add_quiz_button);
        create_lesson_button = (Button) findViewById(R.id.create_lesson_button);


        initiateLists();
    }

    public void initiateLists() {

        schoolNames = new ArrayList<String>();
        for (School school : database.getSchools()){
            schoolNames.add(school.getName());
            System.out.println(school.getName());
        }


        if(!add_school_button.getText().toString().equals("Lägg till skola")){
        subjectNames = new ArrayList<String>();
            if(!newSchool) {
                for (com.idgi.core.Subject subject : database.getSubjects(add_school_button.getText().toString())) {
                    subjectNames.add(subject.getName());
                }
            }
        }


        if(!add_subject_button.getText().toString().equals("Lägg till ämne")){
        courseNames = new ArrayList<String>();
            if(!newSubject) {
                for (Course course : database.getCourses(add_school_button.getText().toString(), add_subject_button.getText().toString())) {
                    courseNames.add(course.getName());
                }
            }
        }

        }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_school_button:
                CreateDialog dialog=new CreateDialog(this, "skola", schoolNames);
                dialog.show();
                break;
            case R.id.add_subject_button:
                CreateDialog dialog1=new CreateDialog(this, "ämne", subjectNames);
                dialog1.show();
                break;
            case R.id.add_course_button:
                CreateDialog dialog2=new CreateDialog(this, "kurs", courseNames);
                dialog2.show();
                break;
            default:
                break;
        }

    }

    public void selectItem(String text, String string) {

        if (text.length()>0) {
            if (string.equals("skola")) {
                setSchool(new School(text));
                add_school_button.setText(text);
                add_subject_button.setEnabled(true);
                newSchool=false;
                initiateLists();

            }
            if (string.equals("ämne")) {
                setSubject(new com.idgi.core.Subject(text));
                add_subject_button.setText(text);
                add_course_button.setEnabled(true);
                newSubject=false;
                initiateLists();

            }
            if (string.equals("kurs")) {
                setCourse(new Course(text));
                add_course_button.setText(text);
                add_quiz_button.setEnabled(true);
                lesson_name_editText.setEnabled(true);
                youtube_url_editText.setEnabled(true);
                create_lesson_button.setEnabled(true);
                newCourse=false;
                initiateLists();

            }
        }
    }

    public void onAddQuizButtonClick(View view) {
        CreateQuizDialog dialog=new CreateQuizDialog(this, questionList);
        dialog.show();

    }

    public void setQuiz() {
        if (questionList.size() > 0) {
            Quiz quiz = new Quiz();
            quiz.addQuestions(questionList);
            Storage.setCurrentQuiz(quiz);
            add_quiz_button.setText("Quiz is added.");
            this.quiz=quiz;
            hasQuiz = true;
        }
    }

    public void onCreateLessonButtonClick(View view){

        Lesson lesson = new Lesson(lesson_name_editText.getText().toString(), new Video(youtube_url_editText.getText().toString()));
        if (hasQuiz){
            lesson.setQuiz(quiz);
        }
        course.addLesson(lesson);
        if(newCourse){
            subject.addCourse(course);
        }
        if(newSubject){
            school.addSubject(subject);
        }
        if(newSchool){
            database.pushSchool(school);
        }
        Storage.setCurrentLesson(lesson);

        startActivity(new Intent(CreateLessonActivity.this, LessonActivity.class));
    }

    public void setSchool(School school) {
        this.school=school;
        this.newSchool=true;
    }

    public void setSubject(com.idgi.core.Subject subject) {
        this.subject=subject;
        this.newSubject = true;
    }

    public void setCourse(Course course) {
        this.course=course;
        this.newCourse=true;
    }

}
