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
import com.idgi.core.IQuiz;
import com.idgi.core.Lesson;
import com.idgi.core.Nameable;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.core.Video;
import com.idgi.event.CreateQuizBus;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.core.ModelUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateLessonActivity extends AppCompatActivity implements CreateQuizBus.Listener {

    /**
     * The type of a list-item that has a name attribute
     */
    private enum ItemType {
        SCHOOL, SUBJECT, COURSE
    }

    private FireDatabase database = FireDatabase.getInstance();
    private EditText txtLessonName, txtYouTubeUrl;
    private ArrayList<String> schoolNames, subjectNames, courseNames;
    private ArrayList<Question> questionList;
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

        txtLessonName = (EditText) findViewById(R.id.lesson_name_editText);
        txtYouTubeUrl = (EditText) findViewById(R.id.youtube_url_editText);
        btnAddSchool = (Button) findViewById(R.id.add_school_button);
        btnAddSubject = (Button) findViewById(R.id.add_subject_button);
        btnAddCourse = (Button) findViewById(R.id.add_course_button);
        btnAddQuiz = (Button) findViewById(R.id.add_quiz_button);
        btnCreateLesson = (Button) findViewById(R.id.create_lesson_button);

        schoolNames = new ArrayList<>();
        subjectNames = new ArrayList<>();
        courseNames = new ArrayList<>();
        questionList = new ArrayList<>();

        for (School school : database.getSchools())
            schoolNames.add(school.getName());

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        loadPreviousData();

        //Inserting a youtube url by choosing it inside the youtube app
        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)) {
                insertYoutubeLink(intent);
            }
        }
    }

    private void loadPreviousData() {
        if (selectedSchool != null) {
            btnAddSchool.setText(selectedSchool.getName());
            btnAddSubject.setEnabled(true);
            refreshSubjects();
        }
        if (selectedSubject != null) {
            btnAddSubject.setText(selectedSubject.getName());
            btnAddCourse.setEnabled(true);
            refreshCourses();
        }
        if (selectedCourse != null) {
            btnAddCourse.setText(selectedCourse.getName());
            btnAddQuiz.setEnabled(true);
            txtLessonName.setEnabled(true);
            txtYouTubeUrl.setEnabled(true);
            btnCreateLesson.setEnabled(true);
        }
        if (selectedQuiz != null) {
            setSelectedQuiz(selectedQuiz);
        }

        if (lessonName != null)
            txtLessonName.setText(lessonName);

        if (youtubeUrl != null)
            txtYouTubeUrl.setText(youtubeUrl);
    }
    public void onClick(View view) {
        final ItemType requestedType;

		List<String> itemList = null;

        switch (view.getId()) {
            case R.id.add_school_button:
                requestedType = ItemType.SCHOOL;
				itemList = schoolNames;
                break;
            case R.id.add_subject_button:
                requestedType = ItemType.SUBJECT;
				itemList = subjectNames;
                break;
            case R.id.add_course_button:
                requestedType = ItemType.COURSE;
				itemList = courseNames;
                break;
            default:
                requestedType = null;
                break;
        }

        showSelectionDialog(itemList, requestedType);
    }

    private void showSelectionDialog(List<String> list, final ItemType requestedType) {
        if (list != null) {
            SelectNameableDialog dialog = new SelectNameableDialog(this, getItemTypeName(requestedType), list);
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    SelectNameableDialog selectionDialog = (SelectNameableDialog) dialog;
                    selectItem(selectionDialog.getSelectedItemText(), requestedType);
                }
            });
        }
    }

    public void selectItem(String name, ItemType type) {
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

            if (btnEnable != null && btnText != null) {
                btnEnable.setEnabled(true);
                btnText.setText(name);
            }
        }
    }

    public void onPause() {
        super.onPause();

        lessonName = txtLessonName.getText().toString();
        youtubeUrl = txtYouTubeUrl.getText().toString();
    }

    private void updateSelectedSchool(String schoolName) {
        School existingSchool = ModelUtility.findByName(database.getSchools(), schoolName);
        School school = existingSchool == null ? new School(schoolName) : existingSchool;
        setSelectedSchool(school);
    }

    private void updateSelectedSubject(String subjectName) {
        if (selectedSchool != null) {
            Subject existingSubject = ModelUtility.findByName(selectedSchool.getSubjects(), subjectName);
            Subject subject = existingSubject == null ? new Subject(subjectName) : existingSubject;
            setSelectedSubject(subject);
        }
    }

    private void updateSelectedCourse(String courseName) {
        if (selectedSubject != null) {
            Course existingCourse = ModelUtility.findByName(selectedSubject.getCourses(), courseName);
            Course course = existingCourse == null ? new Course(courseName) : existingCourse;
            setSelectedCourse(course);
        }
    }

    private void refreshSubjects() {
        subjectNames = new ArrayList<>();

        for (Subject subject : selectedSchool.getSubjects()) {
            subjectNames.add(subject.getName());
        }
    }

    private void refreshCourses() {
        courseNames = new ArrayList<>();

        for (Course course : selectedSubject.getCourses())
            courseNames.add(course.getName());
    }

    private void enableViews(View... views) {
        for (View view : views)
            view.setEnabled(true);
    }

    public void onAddQuizButtonClick(View view) {
        CreateQuizDialog dialog = new CreateQuizDialog(this, questionList);
		dialog.addListener(this);
        dialog.show();
    }

    public void onCreateLessonButtonClick(View view){

        if(isYoutubeLink(txtYouTubeUrl.getText().toString())){

            String videoUrl = trimYoutubeLink(txtYouTubeUrl.getText().toString());
            String lessonName = txtLessonName.getText().toString();

            Video video = Video.from(videoUrl);


            Lesson lesson = new Lesson(lessonName).withVideo(video);
            if (selectedQuiz != null)
                lesson = lesson.withQuiz(selectedQuiz);

            selectedCourse.addLesson(lesson);

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
        if (!isNewSchool(selectedSchool))
            database.pushLessonToSchool(lesson, selectedSchool.getKey(), selectedSubject.getName(), selectedCourse.getName());
        else
            database.pushSchool(selectedSchool);
    }

    private boolean isNewSchool(School school) {
        return !database.getSchools().contains(school);
    }

    public void setSelectedSchool(School school) {
        selectedSchool = school;

        clearChildData(ItemType.SCHOOL);

        refreshSubjects();
    }

    public void setSelectedSubject(Subject subject) {
        selectedSubject = subject;

        if (selectedSchool != null)
            selectedSchool.addSubject(subject);

        clearChildData(ItemType.SUBJECT);

        refreshSubjects();
        refreshCourses();
    }

    private void setSelectedCourse(Course course) {
        selectedCourse = course;

        if (selectedSubject != null)
            selectedSubject.addCourse(course);

        clearChildData(ItemType.COURSE);
        refreshCourses();
    }

    private void setSelectedQuiz(IQuiz quiz) {
        String text = getResources().getString(R.string.create_lesson_quiz_has_been_added);
        btnAddQuiz.setText(text);
        selectedQuiz = quiz;
    }

    private void clearChildData(ItemType type) {
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

	private void clearSubject() {
		if (selectedSubject != null) {
            CreateLessonActivity.selectedSubject = null;
			courseNames.clear();
			String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "ämne");
			btnAddSubject.setText(text);
			btnAddCourse.setEnabled(false);
		}
	}

    private void clearCourse() {
        CreateLessonActivity.selectedCourse = null;

		String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "kurs");
        btnAddCourse.setText(text);
		btnAddQuiz.setEnabled(false);
    }

	private void clearQuiz() {
		questionList.clear();
        CreateLessonActivity.selectedQuiz = null;
		String text = String.format(Locale.ENGLISH, getResources().getString(R.string.create_lesson_add_item), "quiz");
		btnAddQuiz.setText(text);
	}

	/* Returns a readable name of the ItemType from resources */
	private String getItemTypeName(ItemType itemType) {
		switch (itemType) {
			case SCHOOL:
				return getResources().getString(R.string.school);
			case SUBJECT:
				return getResources().getString(R.string.subject);
			case COURSE:
				return getResources().getString(R.string.course);
		}

		return null;
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
    public void onQuizCreated(IQuiz quiz) {
        setSelectedQuiz(quiz);
    }
}
