package com.idgi.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.CreateQuestionDialog;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.recycleViews.adapters.CreateAdapter;
import com.idgi.recycleViews.adapters.CreateQuestionAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.util.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CreateQuizDialog extends Dialog implements View.OnClickListener {

    private CreateLessonActivity c;
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private CreateQuestionAdapter adapter;
    private FireDatabase database;
    private Button add_question_button;
    private Button question_done_button;

    private List<Question> questionList;

    public CreateQuizDialog(CreateLessonActivity a, List<Question> questionList) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.questionList=questionList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        database = FireDatabase.getInstance();
        setContentView(R.layout.create_quiz_dialog);

        add_question_button = (Button) findViewById(R.id.add_question_button);
        question_done_button = (Button) findViewById(R.id.question_done_button);

        add_question_button.setOnClickListener(this);
        question_done_button.setOnClickListener(this);

        manager = new LinearLayoutManager(c);
        recyclerView = (RecyclerView) findViewById(R.id.create_quiz_list_recycler_view);
        recyclerView.setLayoutManager(manager);

        initiateList();
    }

    private void initiateList() {
        ArrayList<String> questionNames = new ArrayList<String>();
        for (Question question : questionList) {
            questionNames.add(question.getText());
        }
        adapter = new CreateQuestionAdapter(this.getContext(), questionNames);
        recyclerView.setAdapter(adapter);

    }


    public void updateQuestionList(Question question) {
        questionList.add(question);
        initiateList();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_question_button) {
            CreateQuestionDialog dialog = new CreateQuestionDialog(this, c);
            dialog.show();
        }
        if (v.getId() == R.id.question_done_button) {
            c.setQuiz();
            dismiss();
        }
    }
}
