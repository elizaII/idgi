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
import com.idgi.core.Subject;
import com.idgi.core.Video;
import com.idgi.recycleViews.adapters.CreateAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.util.Storage;
import com.idgi.util.Util;

import java.util.ArrayList;

public class CreateLessonActivity extends AppCompatActivity {

    private FireDatabase database = FireDatabase.getInstance();
    private EditText txtLessonName, txtYouTubeUrl;
    private ArrayList<String> schoolNames, subjectNames, courseNames;
    private ArrayList<Question> questionList;
    private Button btnAddSchool, btnAddSubject, btnAddCourse, btnAddQuiz, btnCreateLesson;

    private School school;
    private Subject subject;
    private Course course;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        questionList=new ArrayList<>();

        txtLessonName = (EditText) findViewById(R.id.lesson_name_editText);
        txtYouTubeUrl = (EditText) findViewById(R.id.youtube_url_editText);
        btnAddSchool = (Button) findViewById(R.id.add_school_button);
        btnAddSubject = (Button) findViewById(R.id.add_subject_button);
        btnAddCourse = (Button) findViewById(R.id.add_course_button);
        btnAddQuiz = (Button) findViewById(R.id.add_quiz_button);
        btnCreateLesson = (Button) findViewById(R.id.create_lesson_button);


        refreshLists();
    }

    public void refreshLists() {
        schoolNames = new ArrayList<>();
        for (School school : database.getSchools()){
            schoolNames.add(school.getName());
            System.out.println(school.getName());
        }


        if(!btnAddSchool.getText().toString().equals("Lägg till skola")){
        subjectNames = new ArrayList<>();
            if(school != null) {
                for (com.idgi.core.Subject subject : database.getSubjects(btnAddSchool.getText().toString())) {
                    subjectNames.add(subject.getName());
                }
            }
        }


        if(!btnAddSubject.getText().toString().equals("Lägg till ämne")){
        courseNames = new ArrayList<>();
            if(subject != null) {
                for (Course course : database.getCourses(btnAddSchool.getText().toString(), btnAddSubject.getText().toString())) {
                    courseNames.add(course.getName());
                }
            }
        }

        }

    public void onClick(View v) {
        CreateDialog dialog = null;

        switch (v.getId()) {
            case R.id.add_school_button:
                dialog = new CreateDialog(this, CreateAdapter.Type.SCHOOL, schoolNames);
                break;
            case R.id.add_subject_button:
                dialog = new CreateDialog(this, CreateAdapter.Type.SUBJECT, subjectNames);
                break;
            case R.id.add_course_button:
                dialog = new CreateDialog(this, CreateAdapter.Type.COURSE, courseNames);
                break;
            default:
                break;
        }

        if (dialog != null)
            dialog.show();

    }

    private void setEnabledAndText(Button btnEnable, Button btnText, String text) {
        btnEnable.setEnabled(true);
        btnText.setText(text);
    }

    public void selectItem(String text, CreateAdapter.Type type) {

        if (text.length() > 0) {
            Button btnEnable = null;
            Button btnText = null;

            switch (type) {
                case SCHOOL:
                    setSchool(new School(text));
                    btnText = btnAddSchool;
                    btnEnable = btnAddSubject;
                    break;
                case SUBJECT:
                    setSubject(new Subject(text));
                    btnText = btnAddSubject;
                    btnEnable = btnAddCourse;
                    break;
                case COURSE:
                    setCourse(new Course(text));
                    btnText = btnAddCourse;
                    btnEnable = btnAddQuiz;
                    enableViews(txtLessonName, txtYouTubeUrl, btnCreateLesson);
                    break;
            }

            if (btnEnable != null && btnText != null)
                setEnabledAndText(btnEnable, btnText, text);

            refreshLists();
        }
    }

    private void enableViews(View... views) {
        for (View view : views)
            view.setEnabled(true);
    }

    public void onAddQuizButtonClick(View view) {
        CreateQuizDialog dialog = new CreateQuizDialog(this, questionList);
        dialog.show();
    }

    public void setQuiz() {
        if (questionList.size() > 0) {
            Quiz quiz = new Quiz();
            quiz.addQuestions(questionList);
            Storage.setCurrentQuiz(quiz);
            btnAddQuiz.setText("Quiz is added.");
            this.quiz = quiz;
        }
    }

    public void onCreateLessonButtonClick(View view){

        String lessonName = txtLessonName.getText().toString();
        Video video = Video.from(txtYouTubeUrl.getText().toString());

        Lesson lesson = new Lesson(lessonName).withVideo(video).withQuiz(quiz);
        course.addLesson(lesson);

        if (!school.hasSubject(subject))
            school.addSubject(subject);

        if (!subject.hasCourse(course))
            subject.addCourse(course);

        if (!isNewSchool(school))
            database.pushLessonToSchool(lesson, school.getKey(), subject.getName(), course.getName());
        else
            database.pushSchool(school);


        Storage.setCurrentLesson(lesson);

        startActivity(new Intent(CreateLessonActivity.this, LessonActivity.class));
    }

    private boolean isNewSchool(School school) {
        return Util.listContains(database.getSchools(), school);
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
