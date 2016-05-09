package com.idgi.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.idgi.R;
import com.idgi.activities.CreateLessonActivity;
import com.idgi.activities.CreateQuizDialog;
import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.recycleViews.adapters.CreateQuestionAdapter;
import com.idgi.services.FireDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allex on 2016-05-09.
 */
public class CreateQuestionDialog extends Dialog implements
        View.OnClickListener {

    private CreateQuizDialog c;
    private FireDatabase database;
    private Button create_quiz_button;
    private EditText quiz_question_editText;
    private EditText quiz_hint_editText;
    private EditText quiz_answer_editText1;
    private EditText quiz_answer_editText2;
    private EditText quiz_answer_editText3;
    private EditText quiz_answer_editText4;
    private Switch quiz_set_correct_switch1;
    private Switch quiz_set_correct_switch2;
    private Switch quiz_set_correct_switch3;
    private Switch quiz_set_correct_switch4;

    //private EditText

    public CreateQuestionDialog(CreateQuizDialog a, Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        database= FireDatabase.getInstance();
        setContentView(R.layout.create_question_dialog);

        quiz_question_editText = (EditText) findViewById(R.id.quiz_question_editText);
        quiz_hint_editText = (EditText) findViewById(R.id.quiz_hint_editText);
        quiz_answer_editText1 = (EditText) findViewById(R.id.quiz_answer_editText1);
        quiz_answer_editText2 = (EditText) findViewById(R.id.quiz_answer_editText2);
        quiz_answer_editText3 = (EditText) findViewById(R.id.quiz_answer_editText3);
        quiz_answer_editText4 = (EditText) findViewById(R.id.quiz_answer_editText4);
        quiz_set_correct_switch1 = (Switch) findViewById(R.id.quiz_set_correct_switch1);
        quiz_set_correct_switch2 = (Switch) findViewById(R.id.quiz_set_correct_switch2);
        quiz_set_correct_switch3 = (Switch) findViewById(R.id.quiz_set_correct_switch3);
        quiz_set_correct_switch4 = (Switch) findViewById(R.id.quiz_set_correct_switch4);
        create_quiz_button = (Button) findViewById(R.id.create_quiz_button);
        create_quiz_button.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_quiz_button:
                    Question question = new Question(quiz_question_editText.getText().toString());
                Answer answer1 = new Answer(quiz_answer_editText1.getText().toString());
                Answer answer2 = new Answer(quiz_answer_editText2.getText().toString());
                Answer answer3 = new Answer(quiz_answer_editText3.getText().toString());
                Answer answer4 = new Answer(quiz_answer_editText4.getText().toString());

                answer1.setCorrect(quiz_set_correct_switch1.isChecked());
                answer2.setCorrect(quiz_set_correct_switch2.isChecked());
                answer3.setCorrect(quiz_set_correct_switch3.isChecked());
                answer4.setCorrect(quiz_set_correct_switch4.isChecked());

                if(quiz_hint_editText.getText().toString().length()>0) {
                    question.setHint(quiz_hint_editText.getText().toString());
                }
                question.addAnswers(answer1, answer2, answer3, answer4);

                c.updateQuestionList(question);

                break;
            default:
                break;
        }

        dismiss();

    }
}

