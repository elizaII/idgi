package com.idgi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.core.Question;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

	private List<Question> questions;

	public class ViewHolder extends RecyclerView.ViewHolder {
		public ImageView dropdown, image;
		public TextView text;

		public ViewHolder(View view) {
			super(view);
			dropdown = (ImageView) view.findViewById(R.id.quiz_result_list_item_dropdown);
			text = (TextView) view.findViewById(R.id.quiz_result_list_item_question_text);
			image = (ImageView) view.findViewById(R.id.quiz_result_list_item_correctness_icon);
		}
	}

	public QuizResultAdapter(List<Question> questions) {
		this.questions = questions;
	}

	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_quiz_result, parent, false);

		return new ViewHolder(itemView);
	}

	public void onBindViewHolder(ViewHolder holder, int position) {
		Question question = questions.get(position);

		holder.text.setText(question.getText());
		holder.image.setImageResource(getCorrectnessImage(question.isCorrectlyAnswered()));

	}

	private int getCorrectnessImage(boolean correct) {
		return correct ? R.drawable.ic_done : R.drawable.ic_red_cross;
	}

	public int getItemCount() {
		return questions.size();
	}
}
