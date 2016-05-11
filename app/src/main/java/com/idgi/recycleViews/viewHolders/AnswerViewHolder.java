package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Answer;

public class AnswerViewHolder extends ChildViewHolder {

	public TextView txtAnswer;

	public AnswerViewHolder(View itemView) {
		super(itemView);

		txtAnswer = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_text);
	}
}
