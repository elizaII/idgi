package com.idgi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.Widgets.CreateQuestionDialog;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.recycleViews.adapters.CreateQuestionAdapter;
import com.idgi.util.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CreateQuizActivity extends AppCompatActivity {

    //private EditText create_quiz_editText;
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private CreateQuestionAdapter adapter;

    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);




        //create_quiz_editText = (EditText) findViewById(R.id.create_quiz_editText);

        questionList = new ArrayList<Question>();


        manager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.create_quiz_list_recycler_view);
        recyclerView.setLayoutManager(manager);

        initiateList();

    }

    private void initiateList(){
        ArrayList<String> questionNames = new ArrayList<String>();
        for(Question question: questionList){
            questionNames.add(question.getText());
        }
        adapter = new CreateQuestionAdapter(this, questionNames);
        recyclerView.setAdapter(adapter);

    }

    public void onAddQuestionClick(View view){
        CreateQuestionDialog dialog=new CreateQuestionDialog(this);
        dialog.show();
    }

    public void onDoneButtonClick(View view){

        Quiz quiz = new Quiz();
        quiz.addQuestions(questionList);

        Storage.setCurrentQuiz(quiz);
        Intent intent = new Intent(CreateQuizActivity.this, CreateLessonActivity.class);
        intent.putExtra("quiz", "quiz");
        startActivity(intent);


    }

    public void updateQuestionList(Question question) {
        questionList.add(question);
        initiateList();
    }
}
