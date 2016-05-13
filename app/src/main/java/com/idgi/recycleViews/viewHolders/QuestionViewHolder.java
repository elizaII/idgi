package com.idgi.recycleViews.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.idgi.R;
public class QuestionViewHolder extends RecyclerView.ViewHolder {

	public View background;
	public TextView txtQuestion;

	public QuestionViewHolder(View itemView) {
		super(itemView);

		txtQuestion = (TextView) itemView.findViewById(R.id.quiz_result_question_text);
		background = itemView.findViewById(R.id.quiz_result_listitem_background);
	}
}
