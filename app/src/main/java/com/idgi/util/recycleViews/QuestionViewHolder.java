package com.idgi.util.recycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.core.Question;

public class QuestionViewHolder extends ParentViewHolder {

	private ImageView imgCorrectness;
	private TextView txtQuestion;

	public QuestionViewHolder(View itemView) {
		super(itemView);

		txtQuestion = (TextView) itemView.findViewById(R.id.quiz_result_list_item_question_text);
		imgCorrectness = (ImageView) itemView.findViewById(R.id.quiz_result_list_item_correctness_icon);
	}

	public void bind(Question question) {
		txtQuestion.setText(question.getText());
		imgCorrectness.setImageResource(getCorrectnessImage(question.isCorrectlyAnswered()));
	}

	private int getCorrectnessImage(boolean correct) {
		return correct ? R.drawable.ic_done : R.drawable.ic_red_cross;
	}
}
