package com.idgi.recycleViews.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.idgi.R;

public class NewAnswerViewHolder extends RecyclerView.ViewHolder {

	public TextView answerTextView;

	public NewAnswerViewHolder(View itemView) {
		super(itemView);

		answerTextView = (TextView) itemView.findViewById(R.id.quiz_result_listitem_answer_text);
	}
}