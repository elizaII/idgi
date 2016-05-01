package com.idgi;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.core.Answer;
import com.idgi.core.Question;
import com.idgi.util.ExpandAnimation;

import java.util.List;

public class QuizResultAnswerAdapter extends RecyclerView.Adapter<QuizResultAnswerAdapter.ViewHolder> {

	private List<Answer> answers;

	private View.OnClickListener itemClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			View testView = view.findViewById(R.id.quiz_result_recycler_view_answer);

			// TODO Remove?
			/*ExpandAnimation animation = new ExpandAnimation(testView, 500);
			testView.startAnimation(animation);*/
		}
	};

	public class ViewHolder extends RecyclerView.ViewHolder {

		public TextView answerText, correctNotifierText;

		public ViewHolder(View view) {
			super(view);
			answerText = (TextView) view.findViewById(R.id.quiz_result_answer_text);
			correctNotifierText = (TextView) view.findViewById(R.id.quiz_result_answer_correct_text);
		}
	}

	public QuizResultAnswerAdapter(List<Answer> answers) {
		this.answers = answers;
	}

	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_result_listitem_answer, parent, false);

		itemView.setOnClickListener(itemClickListener);

		return new ViewHolder(itemView);
	}

	public void onBindViewHolder(ViewHolder holder, int position) {
		Answer answer = answers.get(position);

		holder.answerText.setText(answer.getText());

		String text = answer.isCorrect() ? "CORRECT" : "";
		holder.correctNotifierText.setText(text);
	}

	public int getItemCount() {
		return answers.size();
	}
}
