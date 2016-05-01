package com.idgi;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.core.Question;
import com.idgi.util.DividerItemDecoration;
import com.idgi.util.ExpandAnimation;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

	private List<Question> questions;
	private QuizResultAnswerAdapter answerAdapter;

	private View.OnClickListener itemClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			View testView = view.findViewById(R.id.quiz_result_recycler_view_answer);

			ExpandAnimation animation = new ExpandAnimation(testView, 500);
			testView.startAnimation(animation);
		}
	};

	public class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView dropdown, image;
		public TextView text;
		public RecyclerView answerList;

		public ViewHolder(View view) {
			super(view);

			dropdown = (ImageView) view.findViewById(R.id.quiz_result_list_item_dropdown);
			text = (TextView) view.findViewById(R.id.quiz_result_list_item_question_text);
			image = (ImageView) view.findViewById(R.id.quiz_result_list_item_correctness_icon);
			answerList = (RecyclerView) view.findViewById(R.id.quiz_result_recycler_view_answer);

			setupAnswerList(view);

		}

		private void setupAnswerList(View view) {
			RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
			answerList.setLayoutManager(layoutManager);
			answerList.addItemDecoration(new DividerItemDecoration(view.getContext()));
		}
	}

	public QuizResultAdapter(List<Question> questions) {
		this.questions = questions;
	}

	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_result_listitem_question, parent, false);

		itemView.setOnClickListener(itemClickListener);

		return new ViewHolder(itemView);
	}

	public void onBindViewHolder(ViewHolder holder, int position) {
		Question question = questions.get(position);

		holder.text.setText(question.getText());
		holder.image.setImageResource(getCorrectnessImage(question.isCorrectlyAnswered()));

		answerAdapter = new QuizResultAnswerAdapter(question.getAnswers());
		holder.answerList.setAdapter(answerAdapter);
	}

	private int getCorrectnessImage(boolean correct) {
		return correct ? R.drawable.ic_done : R.drawable.ic_red_cross;
	}

	public int getItemCount() {
		return questions.size();
	}
}
