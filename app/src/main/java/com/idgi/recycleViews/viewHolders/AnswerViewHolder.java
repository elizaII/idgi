package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;

public class AnswerViewHolder extends ChildViewHolder {

	public TextView answerTextView, feedbackTextView;

	public AnswerViewHolder(View itemView) {
		super(itemView);

		answerTextView = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_text);
		feedbackTextView = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_selected_text);
	}
}
