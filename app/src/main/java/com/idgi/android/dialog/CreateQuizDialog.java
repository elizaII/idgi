package com.idgi.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.core.Question;
import com.idgi.core.Quiz;
import com.idgi.event.ApplicationBus;
import com.idgi.android.recycleView.adapters.CreateQuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizDialog extends Dialog {

    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private CreateQuestionAdapter adapter;
    private Button add_question_button;
    private Button question_done_button;

    private List<Question> questionList;
	public List<String> questionNames;

    public CreateQuizDialog(Context context, List<Question> questionList) {
        super(context);
        this.questionList = questionList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_quiz_dialog);

		//Register as a subscriber
		ApplicationBus.register(this);

        add_question_button = (Button) findViewById(R.id.add_question_button);
        question_done_button = (Button) findViewById(R.id.question_done_button);

        add_question_button.setOnClickListener(onCreateQuestionClick);
        question_done_button.setOnClickListener(onDoneClick);

        manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) findViewById(R.id.create_quiz_list_recycler_view);
        recyclerView.setLayoutManager(manager);

        initiateList();
    }

	private void initiateList() {
        questionNames = new ArrayList<>();
        for (Question question : questionList) {
            questionNames.add(question.getText());
        }
        adapter = new CreateQuestionAdapter(this.getContext(), questionNames);
        recyclerView.setAdapter(adapter);
    }

	private void updateQuestionList(Question question) {
		questionList.add(question);
		questionNames.add(question.getText());
		adapter.notifyDataSetChanged();
	}

	private final View.OnClickListener onCreateQuestionClick = new View.OnClickListener() {
		public void onClick(View view) {
			CreateQuestionDialog dialog = new CreateQuestionDialog(getContext());
			dialog.show();
		}
	};

	private final View.OnClickListener onDoneClick = new View.OnClickListener() {
		public void onClick(View view) {
			if (!questionList.isEmpty()) {
				Quiz quiz = new Quiz();
				quiz.addQuestions(questionList);
				ApplicationBus.post(quiz);
				dismiss();
			}
		}
	};

	@Subscribe
	public void onQuestionCreated(Question question) {
		updateQuestionList(question);
	}
}
