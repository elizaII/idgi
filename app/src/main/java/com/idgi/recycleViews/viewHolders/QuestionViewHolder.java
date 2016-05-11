package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.core.Question;

public class QuestionViewHolder extends ParentViewHolder {

	private View background;
	private TextView txtQuestion;

	public QuestionViewHolder(View itemView) {
		super(itemView);

		txtQuestion = (TextView) itemView.findViewById(R.id.quiz_result_question_text);
		background = itemView.findViewById(R.id.quiz_result_listitem_background);
	}

	public void bind(Question question) {
		boolean isCorrect = question.isCorrectlyAnswered();

		txtQuestion.setText(question.getText());
		txtQuestion.setTextColor(getTextColor(isCorrect));
		background.setBackgroundResource(getBackground(isCorrect));
	}

	private int getTextColor(boolean correct) {
		return correct ? R.color.quizResultQuestionCorrectText : R.color.quizResultQuestionIncorrectText;
	}

	private int getBackground(boolean correct) {
		return correct ? R.drawable.quiz_result_question_background_correct : R.drawable.quiz_result_question_background_incorrect;
	}
}
