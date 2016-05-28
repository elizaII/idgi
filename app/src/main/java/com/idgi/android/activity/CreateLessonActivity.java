package com.idgi.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.dialog.CreateQuizDialog;
import com.idgi.android.dialog.SelectNameableDialog;
import com.idgi.core.Course;
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.core.NameableType;
import com.idgi.core.Question;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.Video;
import com.idgi.event.ApplicationBus;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
Lets the teacher create a lesson. The teacher may also create a new school, subject and/or
course in conjunction with creating the lesson. A quiz can also be added through this activity.
 */
public class CreateLessonActivityCopy extends AppCompatActivity{

    private FireDatabase db = FireDatabase.getInstance();
    private EditText txtLessonName, txtYouTubeUrl;
    private List<School> schools;
    private List<Subject> subjects;
    private List<Course> courses;
    private ArrayList<Question> questions;
    private Button btnAddSchool, btnAddSubject, btnAddCourse, btnAddQuiz, btnCreateLesson;

    //These are static to preserve state if user navigates away and then returns
    private static School selectedSchool;
    private static Subject selectedSubject;
    private static Course selectedCourse;
    private static IQuiz selectedQuiz;
    private static String lessonName, youtubeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);

        //Register as a subscriber
        ApplicationBus.register(this);

        txtLessonName = (EditText) findViewById(R.id.lesson_name_editText);
        txtYouTubeUrl = (EditText) findViewById(R.id.youtube_url_editText);
        btnAddSchool = (Button) findViewById(R.id.add_school_button);
        btnAddSubject = (Button) findViewById(R.id.add_subject_button);
        btnAddCourse = (Button) findViewById(R.id.add_course_button);
        btnAddQuiz = (Button) findViewById(R.id.add_quiz_button);
        btnCreateLesson = (Button) findViewById(R.id.create_lesson_button);

        schools = db.getSchools();
        subjects = new ArrayList<>();
        courses = new ArrayList<>();
        questions = new ArrayList<>();

        btnAddSchool.setOnClickListener(onAddItemButtonClick(NameableType.SCHOOL));
        btnAddSubject.setOnClickListener(onAddItemButtonClick(NameableType.SUBJECT));
        btnAddCourse.setOnClickListener(onAddItemButtonClick(NameableType.COURSE));
        btnCreateLesson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createLesson();
            }
        });

        loadPreviousData();

        if (YouTubeHelper.isYouTubeIntent(getIntent()))
            insertYoutubeLink(getIntent());
    }

    private void createLesson() {
        String videoUrl = YouTubeHelper.trimLink(txtYouTubeUrl.getText().toString());
        String lessonName = txtLessonName.getText().toString();

        Video video = Video.from(videoUrl);

        Lesson lesson = new Lesson(lessonName).withVideo(video);
        if (selectedQuiz != null)
            lesson = lesson.withQuiz(selectedQuiz);

        selectedCourse.addLesson(lesson);

        pushLesson(lesson);
        SessionData.setCurrentLesson(lesson);

        startActivity(new Intent(this, LessonActivity.class));
        finish();
    }

    private View.OnClickListener onAddItemButtonClick(final NameableType type) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                List<? extends Nameable> nameables = getNameableList(type);
                SelectNameableDialog dialog = new SelectNameableDialog(CreateLessonActivityNew.this, nameables, type);
                dialog.show();
                dialog.setOnDismissListener(onSelectionDialogDismiss);
            }
        };
    }

    private DialogInterface.OnDismissListener onSelectionDialogDismiss = new DialogInterface.OnDismissListener() {
        public void onDismiss(DialogInterface rawDialog) {
            SelectNameableDialog dialog = (SelectNameableDialog) rawDialog;
            onNameableSelected(dialog.getSelectedNameable());
        }
    };

    private void selectSchool(School school) {
        selectedSchool = school;
        if (!schools.contains(school))
            schools.add(school);

        btnAddSchool.setText(school.getName());
        enableButton(btnAddSubject);
        subjects = school.getSubjects();
        clearSubject();
    }

    private void selectSubject(Subject subject) {
        selectedSubject = subject;
        if (!subjects.contains(subject))
            subjects.add(subject);

        btnAddSubject.setText(subject.getName());
        enableButton(btnAddCourse);
        courses = subject.getCourses();
        clearCourse();
    }

    private void selectCourse(Course course) {
        courses.add(course);
        btnAddCourse.setText(course.getName());
        enableViews(btnAddQuiz, txtLessonName, txtYouTubeUrl, btnCreateLesson);
        clearQuiz();
    }

    private void onNameableSelected(Nameable nameable) {
        if (nameable == null)
            return;

        switch (nameable.getType()) {
            case SCHOOL:
                selectSchool((School)nameable);
                break;
            case SUBJECT:
                selectSubject((Subject) nameable);
                break;
            case COURSE:
                selectCourse((Course) nameable);
                break;
            case QUIZ:
                btnAddQuiz.setText(getResources().getString(R.string.create_lesson_quiz_has_been_added));
                selectedQuiz = (IQuiz) nameable;
        }
    }

    private void enableButton(Button button) {
        button.setEnabled(true);
    }

    private void clearSubject() {
        if (selectedSubject != null) {
            selectedSubject = null;
            courses.clear();
            String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), getResources().getString(R.string.subject));
            btnAddSubject.setText(text);
            btnAddCourse.setEnabled(false);
        }
    }

    private void clearCourse() {
        selectedCourse = null;

        String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), getResources().getString(R.string.course));
        btnAddCourse.setText(text);
        btnAddQuiz.setEnabled(false);
    }

    private void clearQuiz() {
        questions.clear();
        selectedQuiz = null;
        String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), getResources().getString(R.string.quiz));
        btnAddQuiz.setText(text);
    }

    private void loadPreviousData() {
        if (selectedSchool != null) {
            btnAddSchool.setText(selectedSchool.getName());
            btnAddSubject.setEnabled(true);
        }
        if (selectedSubject != null) {
            btnAddSubject.setText(selectedSubject.getName());
            btnAddCourse.setEnabled(true);
        }
        if (selectedCourse != null) {
            btnAddCourse.setText(selectedCourse.getName());
            enableViews(btnAddQuiz, txtLessonName, txtYouTubeUrl, btnCreateLesson);
        }

        if (lessonName != null)
            txtLessonName.setText(lessonName);

        if (youtubeUrl != null)
            txtYouTubeUrl.setText(youtubeUrl);
    }

    private void enableViews(View... views) {
        for (View view : views)
            view.setEnabled(true);
    }

    private List<? extends Nameable> getNameableList(NameableType type) {
        switch (type) {
            case SCHOOL:
                return schools;
            case SUBJECT:
                return subjects;
            case COURSE:
                return courses;
            default:
                return null;
        }
    }

    public void onPause() {
        super.onPause();

        lessonName = txtLessonName.getText().toString();
        youtubeUrl = txtYouTubeUrl.getText().toString();
    }

    public void onAddQuizButtonClick(View view) {
        (new CreateQuizDialog(this, questions)).show();
    }

    public void onCreateLessonButtonClick(View view){
        if(YouTubeHelper.isYoutubeLink(txtYouTubeUrl.getText().toString()))
            createLesson();
        else
            YouTubeHelper.showLinkErrorDialog(this);
    }

    private void pushSubject() {
        if (!isNewSchool(selectedSchool))
            db.pushLessonToSchool(selectedSchool, selectedSubject);
        else
            db.pushSchool(selectedSchool);
    }

    private boolean isNewSchool(School school) {
        return !db.getSchools().contains(school);
    }

    // Setting the link to the youtube url that was received from the youtube app
    private void insertYoutubeLink(Intent intent){
        String youtubeURL = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(youtubeURL != null){
            txtYouTubeUrl.setText(youtubeURL);
        }
    }

    @Override
    public void finish(){
        super.finish();

        ApplicationBus.unregister(this);
    }

    @Subscribe
    public void onQuizCreated(IQuiz quiz) {
        onNameableSelected(quiz);
    }

}