package com.idgi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.activities.dialogs.SelectNameableDialog;
import com.idgi.activities.dialogs.CreateQuizDialog;
import com.idgi.activities.extras.ActivityType;
import com.idgi.activities.extras.Navigation;
import com.idgi.recycleViews.adapters.SelectNameableAdapter;
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

import java.util.ArrayList;

public class CreateLessonActivity extends AppCompatActivity implements SelectNameableAdapter.ListChangeListener {

    private FireDatabase database = FireDatabase.getInstance();
    private EditText txtLessonName, txtYouTubeUrl;
    private ArrayList<String> schoolNames, subjectNames, courseNames;
    private ArrayList<Question> questionList;
    private Button btnAddSchool, btnAddSubject, btnAddCourse, btnAddQuiz, btnCreateLesson;

    private School school;
    private Subject subject;
    private Course course;
    private Quiz quiz;

	private SelectNameableDialog activeDialog;

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
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_school_button:
                activeDialog = new SelectNameableDialog(this, Type.SCHOOL, schoolNames);
                break;
            case R.id.add_subject_button:
				activeDialog = new SelectNameableDialog(this, Type.SUBJECT, subjectNames);
                break;
            case R.id.add_course_button:
				activeDialog = new SelectNameableDialog(this, Type.COURSE, courseNames);
                break;
            default:
                break;
        }

        if (activeDialog != null) {
			activeDialog.show();
			activeDialog.setListChangeListener(this);
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
        setSchool(school);
    }

    private void updateSelectedSubject(String subjectName) {
        if (school != null) {
            Subject existingSubject = ModelUtility.findByName(school.getSubjects(), subjectName);
            Subject subject = existingSubject == null ? new Subject(subjectName) : existingSubject;
            setSubject(subject);
        }
    }

    private void updateSelectedCourse(String courseName) {
        if (subject != null) {
            Course existingCourse = ModelUtility.findByName(subject.getCourses(), courseName);
            Course course = existingCourse == null ? new Course(courseName) : existingCourse;
            setCourse(course);
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
        dialog.show();
    }

    public void setQuiz() {
        if (questionList.size() > 0) {
            Quiz quiz = new Quiz();
            quiz.addQuestions(questionList);
            btnAddQuiz.setText("Quiz is added.");
            this.quiz = quiz;
        }
    }

    public void onCreateLessonButtonClick(View view){

        String videoUrl = txtYouTubeUrl.getText().toString();
        String lessonName = txtLessonName.getText().toString();

        Video video = Video.from(videoUrl);
        Lesson lesson = new Lesson(lessonName).withVideo(video).withQuiz(quiz);
        course.addLesson(lesson);

        pushLesson(lesson);
        SessionData.setCurrentLesson(lesson);

        Navigation.startActivity(this, ActivityType.LESSON);
    }

    private void pushLesson(Lesson lesson) {
        if (!isNewSchool(school))
            database.pushLessonToSchool(lesson, school.getKey(), subject.getName(), course.getName());
        else
            database.pushSchool(school);
    }

    private boolean isNewSchool(School school) {
        return database.getSchools().contains(school);
    }

    public void setSchool(School school) {
        this.school = school;

        clearChildData(Type.SCHOOL);

        refreshSubjects();
    }

    public void setSubject(Subject subject) {
        this.subject = subject;

        if (school != null)
            school.addSubject(subject);

        clearChildData(Type.SUBJECT);

        refreshSubjects();
        refreshCourses();
    }

    public void setCourse(Course course) {
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
        btnAddQuiz.setText("Lägg till quiz");
    }

    private void clearCourse() {
        course = null;
        btnAddCourse.setText("Lägg till kurs");
    }

    private void clearSubject() {
        if (subject != null) {
            subject = null;
            courseNames.clear();
            btnAddSubject.setText("Lägg till ämne");
        }
    }

	@Override
	public void receiveItemData(String name, Type type) {
		selectItem(name, type);
		if (activeDialog != null)
		activeDialog.dismiss();

		activeDialog = null;
	}
}
