package com.idgi.recycleViews.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.idgi.R;
import com.idgi.activities.CourseActivity;
import com.idgi.core.Answer;
import com.idgi.core.Course;
import com.idgi.core.Question;
import com.idgi.recycleViews.viewHolders.AnswerViewHolder;
import com.idgi.recycleViews.viewHolders.QuestionViewHolder;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

	private List<Question> questions;
	private LayoutInflater inflater;

	public QuestionAdapter(Context context, List<Question> questions){
		this.questions = questions;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.quiz_result_listitem_question, parent, false);
		return new QuestionViewHolder(view);

	}

	@Override
	public void onBindViewHolder(QuestionViewHolder holder, int position) {
		final Question question = questions.get(position);
		boolean isCorrect = question.isCorrectlyAnswered();
		holder.txtQuestion.setText(question.getText());
		holder.txtQuestion.setTextColor(getTextColor(isCorrect));
		holder.background.setBackgroundResource(getBackground(isCorrect));

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				showAnswerDialog(view.getContext(), question);
			}
		});
	}

	@Override
	public int getItemCount() {
		return questions.size();
	}

	private int getTextColor(boolean correct) {
		return correct ? R.color.quizResultQuestionCorrectText : R.color.quizResultQuestionIncorrectText;
	}

	private int getBackground(boolean correct) {
		return correct ? R.drawable.quiz_result_question_background_correct : R.drawable.quiz_result_question_background_incorrect;
	}

	private void showAnswerDialog(Context context, Question question) {
		Dialog dialog = new Dialog(context);

		View contentView = inflater.inflate(R.layout.dialog_answers, null);
		dialog.setContentView(contentView);

		RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.dialog_answers_list);
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(new AnswerAdapter(question.getAnswers(), R.layout.quiz_result_listitem_answer));
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		dialog.show();

		dialog.getWindow().setGravity(Gravity.CENTER);
		dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	}
}
