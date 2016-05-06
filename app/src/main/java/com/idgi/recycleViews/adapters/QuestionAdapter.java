package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.recycleViews.viewHolders.AnswerViewHolder;
import com.idgi.recycleViews.viewHolders.QuestionViewHolder;

import java.util.List;

public class QuestionAdapter extends ExpandableRecyclerAdapter<QuestionViewHolder, AnswerViewHolder> {


	private LayoutInflater inflater;

	public QuestionAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
		super(parentListItem);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public QuestionViewHolder onCreateParentViewHolder(ViewGroup questionViewGroup) {
		View questionView = inflater.inflate(R.layout.quiz_result_listitem_question, questionViewGroup, false);
		return new QuestionViewHolder(questionView);
	}

	@Override
	public AnswerViewHolder onCreateChildViewHolder(ViewGroup answerViewGroup) {
		View answerView = inflater.inflate(R.layout.quiz_result_listitem_answer, answerViewGroup, false);
		return new AnswerViewHolder(answerView);
	}

	@Override
	public void onBindParentViewHolder(QuestionViewHolder questionViewHolder, int position, ParentListItem parentListItem) {
		Question question = (Question) parentListItem;
		questionViewHolder.bind(question);
	}

	@Override
	public void onBindChildViewHolder(AnswerViewHolder answerViewHolder, int position, Object childListItem) {
		Answer answer = (Answer) childListItem;
		answerViewHolder.bind(answer);
	}
}
