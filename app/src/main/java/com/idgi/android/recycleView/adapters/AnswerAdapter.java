package com.idgi.android.recycleView.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Answer;

import java.util.List;

/*
Used by the RecycleView that holds the Answers post-quiz.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

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
		return correct ?
				ContextCompat.getColor(context, R.color.quizResultQuestionCorrectText) :
				Color.BLACK;
	}

	private String getFeedbackText(boolean selected, boolean correct) {
		if (!selected)
			return "";

		int resource = correct ? R.string.quiz_result_answer_correct : R.string.quiz_result_answer_incorrect;
		return context.getResources().getString(resource);
	}

    public static class AnswerViewHolder extends ChildViewHolder {

        public TextView answerTextView, feedbackTextView;

        public AnswerViewHolder(View itemView) {
            super(itemView);

            answerTextView = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_text);
            feedbackTextView = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_selected_text);
        }
    }
}