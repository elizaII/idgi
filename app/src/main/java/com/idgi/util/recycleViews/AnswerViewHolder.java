package com.idgi.util.recycleViews;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Answer;

public class AnswerViewHolder extends ChildViewHolder {

	private TextView txtAnswer, txtCorrectStatus;

	public AnswerViewHolder(View itemView) {
		super(itemView);

		txtAnswer = (TextView) itemView.findViewById(R.id.quiz_result_answer_text);
		txtCorrectStatus = (TextView) itemView.findViewById(R.id.quiz_result_answer_correct_text);
	}

	public void bind(Answer answer) {
		txtAnswer.setText(answer.getText());
		txtCorrectStatus.setText(getStatusText(answer));
	}

	private String getStatusText(Answer answer) {
		return answer.isCorrect() ? "CORRECT" : "";
	}
}
