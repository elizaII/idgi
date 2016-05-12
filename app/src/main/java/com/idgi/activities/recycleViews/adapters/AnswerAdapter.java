package com.idgi.activities.recycleViews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.activities.recycleViews.viewHolders.AnswerViewHolder;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerViewHolder> {

	private List<Answer> answers;
	private int itemLayout;
	private Context context;

	public AnswerAdapter(List<Answer> answers, int itemLayout) {
		this.answers = answers;
		this.itemLayout = itemLayout;
	}

	@Override
	public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
		context = parent.getContext();
		return new AnswerViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AnswerViewHolder holder, int position) {
		Answer answer = answers.get(position);

		int color = getColor(answer.isCorrect());
		String text = answer.getText();
		String feedbackText = getFeedbackText(answer.isSelected(), answer.isCorrect());


		holder.answerTextView.setText(text);
		holder.answerTextView.setTextColor(color);
		holder.feedbackTextView.setText(feedbackText);
	}

	@Override public int getItemCount() {
		return answers.size();
	}

	private int getColor(boolean correct) {
		if (correct) {
			if (Build.VERSION.SDK_INT >= 23)
				return context.getResources().getColor(R.color.quizResultQuestionCorrectText, context.getTheme());
			else
				return context.getResources().getColor(R.color.quizResultQuestionCorrectText);
		}
		else
			return Color.BLACK;
	}

	private String getFeedbackText(boolean selected, boolean correct) {
		if (!selected)
			return "";

		int resource = correct ? R.string.quiz_result_answer_correct : R.string.quiz_result_answer_incorrect;
		return context.getResources().getString(resource);
	}
}