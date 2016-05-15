package com.idgi.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.activities.dialogs.SelectNameableDialog;
import com.idgi.activities.dialogs.CreateQuizDialog;
import com.idgi.core.Course;
import com.idgi.core.Lesson;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.Video;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.Type;
import com.idgi.core.ModelUtility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Locale;

public class CreateLessonActivity extends AppCompatActivity implements PropertyChangeListener {

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

        questionList = new ArrayList<>();

        txtLessonName = (EditText) findViewById(R.id.lesson_name_editText);
        txtYouTubeUrl = (EditText) findViewById(R.id.youtube_url_editText);
        btnAddSchool = (Button) findViewById(R.id.add_school_button);
        btnAddSubject = (Button) findViewById(R.id.add_subject_button);
        btnAddCourse = (Button) findViewById(R.id.add_course_button);
        btnAddQuiz = (Button) findViewById(R.id.add_quiz_button);
        btnCreateLesson = (Button) findViewById(R.id.create_lesson_button);

        schoolNames = new ArrayList<>();
        for (School school : database.getSchools())
            schoolNames.add(school.getName());

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        //Inserting a youtube url by choosing it inside the youtube app
        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)) {
                insertYoutubeLink(intent);
            }
        }
    }

    public void onClick(View view) {
		SelectNameableDialog dialog = null;

        switch (view.getId()) {
            case R.id.add_school_button:
				dialog = new SelectNameableDialog(this, Type.SCHOOL, schoolNames);
                break;
            case R.id.add_subject_button:
				dialog = new SelectNameableDialog(this, Type.SUBJECT, subjectNames);
                break;
            case R.id.add_course_button:
				dialog = new SelectNameableDialog(this, Type.COURSE, courseNames);
                break;
            default:
                break;
        }

        if (dialog != null) {
			dialog.show();
			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					SelectNameableDialog selectionDialog = (SelectNameableDialog) dialog;
					selectItem(selectionDialog.getSelectedItemName(), selectionDialog.getItemType());
				}
			});
        }

    }

    private void setEnabledAndText(Button btnEnable, Button btnText, String text) {
        btnEnable.setEnabled(true);
        btnText.setText(text);
    }

    public void selectItem(String name, Type type) {
        if (name.length() > 0) {
            Button btnEnable = null;
            Button btnText = null;

            switch (type) {
                case SCHOOL:
                    updateSelectedSchool(name);
                    btnText = btnAddSchool;
                    btnEnable = btnAddSubject;
                    break;
                case SUBJECT:
                    updateSelectedSubject(name);
                    btnText = btnAddSubject;
                    btnEnable = btnAddCourse;
                    break;
                case COURSE:
                    updateSelectedCourse(name);
                    btnText = btnAddCourse;
                    btnEnable = btnAddQuiz;
                    enableViews(txtLessonName, txtYouTubeUrl, btnCreateLesson);
                    break;
            }

            if (btnEnable != null && btnText != null)
                setEnabledAndText(btnEnable, btnText, name);
        }
    }

    private void updateSelectedSchool(String schoolName) {
        School existingSchool = ModelUtility.findByName(database.getSchools(), schoolName);
        School school = existingSchool == null ? new School(schoolName) : existingSchool;
        setSelectedSchool(school);
    }

    private void updateSelectedSubject(String subjectName) {
        if (school != null) {
            Subject existingSubject = ModelUtility.findByName(school.getSubjects(), subjectName);
            Subject subject = existingSubject == null ? new Subject(subjectName) : existingSubject;
            setSelectedSubject(subject);
        }
    }

    private void updateSelectedCourse(String courseName) {
        if (subject != null) {
            Course existingCourse = ModelUtility.findByName(subject.getCourses(), courseName);
            Course course = existingCourse == null ? new Course(courseName) : existingCourse;
            setSelectedCourse(course);
        }
    }

    private void refreshSubjects() {
        subjectNames = new ArrayList<>();

        for (Subject subject : school.getSubjects()) {
            subjectNames.add(subject.getName());
        }
    }

    private void refreshCourses() {
        courseNames = new ArrayList<>();

        for (Course course : subject.getCourses())
            courseNames.add(course.getName());
    }

    private void enableViews(View... views) {
        for (View view : views)
            view.setEnabled(true);
    }

    public void onAddQuizButtonClick(View view) {
        CreateQuizDialog dialog = new CreateQuizDialog(this, questionList);
		dialog.addPropertyChangeListener(this);
        dialog.show();
    }

    public void onCreateLessonButtonClick(View view){

        if(isYoutubeLink(txtYouTubeUrl.getText().toString())){

            String videoUrl = trimYoutubeLink(txtYouTubeUrl.getText().toString());
            String lessonName = txtLessonName.getText().toString();

            Video video = Video.from(videoUrl);
            Lesson lesson = new Lesson(lessonName).withVideo(video).withQuiz(quiz);
            course.addLesson(lesson);

            pushLesson(lesson);
            SessionData.setCurrentLesson(lesson);

            startActivity(new Intent(this, LessonActivity.class));
        } else{

            //Show dialog when user has given an invalid link
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getString(R.string.create_lesson_invalid_link_title))
                    .setMessage(getString(R.string.create_lesson_invalid_link_message))
                    .setPositiveButton(getString(R.string.ok),null)
                    .show();
        }
    }

    private void pushLesson(Lesson lesson) {
        if (!isNewSchool(school))
            database.pushLessonToSchool(lesson, school.getKey(), subject.getName(), course.getName());
        else
            database.pushSchool(school);
    }

    private boolean isNewSchool(School school) {
        return !database.getSchools().contains(school);
    }

    public void setSelectedSchool(School school) {
        this.school = school;

        clearChildData(Type.SCHOOL);

        refreshSubjects();
    }

    public void setSelectedSubject(Subject subject) {
        this.subject = subject;

        if (school != null)
            school.addSubject(subject);

        clearChildData(Type.SUBJECT);

        refreshSubjects();
        refreshCourses();
    }

    public void setSelectedCourse(Course course) {
        this.course = course;

        if (subject != null)
            subject.addCourse(course);

        clearChildData(Type.COURSE);
        refreshCourses();
    }

    private void clearChildData(Type type) {
        //No break is intentional, we want cascading behavior
        switch(type) {
            case SCHOOL:
                clearSubject();
            case SUBJECT:
                clearCourse();
            case COURSE:
                clearQuiz();
        }
    }

    private void clearQuiz() {
        questionList.clear();
        quiz = null;
		String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "quiz");
        btnAddQuiz.setText(text);
    }

    private void clearCourse() {
        course = null;

		String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "kurs");
        btnAddCourse.setText(text);
    }

    private void clearSubject() {
        if (subject != null) {
            subject = null;
            courseNames.clear();
			String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "ämne");
            btnAddSubject.setText(text);
        }
    }

    /**
     * Setting the link to the youtube url that was received from the youtube app
     */
    private void insertYoutubeLink(Intent intent){
        String youtubeURL = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(youtubeURL != null){
            txtYouTubeUrl.setText(youtubeURL);
        }
    }

    private boolean isYoutubeLink(String url){
        return url.contains("youtube.com") || url.contains("youtu.be");
    }


    /**
     * Trims a youtube link to get the unique ID of a video
     * @param url The youtube link that will be trimmed
     * @return The unique identifier for a youtube video
     */
    private String trimYoutubeLink(String url){
        if(url.contains("youtube.com")){

            //A youtube link usually looks like this
            //https://www.youtube.com/watch?v=aE2drlA8vf8
            //where the unique identifier is after the equal sign
            String[] urlFragments = url.split("=");

            //There are times when it can have this appearance
            //https://www.youtube.com/watch?v=aE2drlA8vf8&feature=youtu.be&t=2m
            //so we need to trim the now "aE2drlA8vf8&feature=youtu.be&t=2m"
            //and retrieve the first part
            if(urlFragments[1].contains("&")){
                String[] tokens = urlFragments[1].split("&");
                return tokens[0];
            }

            return urlFragments[1];

        } else if(url.contains("youtu.be")){

            //Similarly for the shortened link
            //https://youtu.be/aE2drlA8vf8
            String[] urlFragments = url.split(".be/");

            //And if it contains more information
            //https://youtu.be/aE2drlA8vf8?t=2m
            if(urlFragments[1].contains("?")){
                String [] tokens = urlFragments[1].split("\\?");
                return tokens[0];
            }

            return urlFragments[1];
        }

        //Returns an empty string, but it shouldn't
        //because the method isYoutubeLink checks
        //if it's a valid link in beforehand
        return "";
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch(event.getPropertyName()) {
            case "quizCreated":
				String text = getResources().getString(R.string.create_lesson_quiz_has_been_added);
                btnAddQuiz.setText(text);
                this.quiz = (Quiz) event.getNewValue();
				break;
        }
    }
}
