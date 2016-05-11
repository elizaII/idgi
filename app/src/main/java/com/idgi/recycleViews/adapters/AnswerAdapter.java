package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Answer;
import com.idgi.recycleViews.viewHolders.NewAnswerViewHolder;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<NewAnswerViewHolder> {

	private List<Answer> answers;
	private int itemLayout;
	private Context context;

	public AnswerAdapter(List<Answer> answers, int itemLayout) {
		this.answers = answers;
		this.itemLayout = itemLayout;
	}

	@Override
	public NewAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
		context = parent.getContext();
		return new NewAnswerViewHolder(view);
	}

	@Override
	public void onBindViewHolder(NewAnswerViewHolder holder, int position) {
		Answer answer = answers.get(position);

		int color = Color.BLACK;

		if (answer.isSelected()) {
			boolean correct = answer.isCorrect();
			color = getColor(correct);
		}

		String text = answer.getText();

		holder.answerTextView.setText(text);
		holder.answerTextView.setTextColor(color);
	}

	@Override public int getItemCount() {
		return answers.size();
	}

	private int getColor(boolean correct) {
		int resource = correct ? R.color.quizResultQuestionCorrectText : R.color.quizResultQuestionIncorrectText;
		return context.getResources().getColor(resource);
	}
}