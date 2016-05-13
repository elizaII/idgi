package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.idgi.R;
import com.idgi.activities.CreateLessonActivity;
import com.idgi.core.Question;
import com.idgi.recycleViews.adapters.CreateQuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizDialog extends Dialog implements View.OnClickListener {

    private CreateLessonActivity c;
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private CreateQuestionAdapter adapter;
    private Button add_question_button;
    private Button question_done_button;

    private List<Question> questionList;

    public CreateQuizDialog(CreateLessonActivity a, List<Question> questionList) {
        super(a);
        this.c = a;
        this.questionList=questionList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        ArrayList<String> questionNames = new ArrayList<>();
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
