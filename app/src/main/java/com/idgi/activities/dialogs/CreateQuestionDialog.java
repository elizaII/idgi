package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.core.Question;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CreateQuestionDialog extends Dialog {

    private Button btnCreateQuiz;
    private EditText txtQuizQuestion;
    private EditText txtQuizHint;
	private EditText[] txtQuizAnswers = new EditText[4];
	private Switch[] correctAnswerSwitches = new Switch[4];

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public CreateQuestionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_question_dialog);

        txtQuizQuestion = (EditText) findViewById(R.id.quiz_question_editText);
        txtQuizHint = (EditText) findViewById(R.id.quiz_hint_editText);
        txtQuizAnswers[0] = (EditText) findViewById(R.id.quiz_answer_editText1);
		txtQuizAnswers[1] = (EditText) findViewById(R.id.quiz_answer_editText2);
		txtQuizAnswers[2] = (EditText) findViewById(R.id.quiz_answer_editText3);
		txtQuizAnswers[3] = (EditText) findViewById(R.id.quiz_answer_editText4);
        correctAnswerSwitches[0] = (Switch) findViewById(R.id.quiz_set_correct_switch1);
		correctAnswerSwitches[1] = (Switch) findViewById(R.id.quiz_set_correct_switch2);
		correctAnswerSwitches[2] = (Switch) findViewById(R.id.quiz_set_correct_switch3);
		correctAnswerSwitches[3] = (Switch) findViewById(R.id.quiz_set_correct_switch4);
        btnCreateQuiz = (Button) findViewById(R.id.create_quiz_button);
        btnCreateQuiz.setOnClickListener(onCreateClick);
    }

	/**
	 * Add the given answers to the new question
	 */
	private void updateAnswers(Question question) {
		for (int i = 0; i < txtQuizAnswers.length; ++i) {
			String answerText = txtQuizAnswers[i].getText().toString();
			boolean isCorrect = correctAnswerSwitches[i].isChecked();
			Answer answer = isCorrect ? Answer.correct(answerText) : Answer.incorrect(answerText);
			question.addAnswer(answer);
		}
	}

	/**
	 * Update the hint for the new question
	 */
	private void updateHint(Question question) {
		if(includeHint()) {
			String hint = txtQuizHint.getText().toString();
			question.setHint(hint);
		}
	}

	/**
	 * Returns true if there is a hint
	 */
	private boolean includeHint() {
		return !txtQuizHint.getText().toString().isEmpty();
	}

	private final View.OnClickListener onCreateClick = new View.OnClickListener() {
		public void onClick(View view) {
			Question question = new Question(txtQuizQuestion.getText().toString());

			updateAnswers(question);
			updateHint(question);
			pcs.firePropertyChange("questionCreated", null, question);
			dismiss();
		}
	};

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
}

