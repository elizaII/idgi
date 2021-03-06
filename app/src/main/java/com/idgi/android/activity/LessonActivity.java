package com.idgi.android.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
 import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.ActivityType;
import com.idgi.android.dialog.CommentReplyDialog;
import com.idgi.android.dialog.LoginRequiredDialog;
import com.idgi.android.dialog.PickQuizDialog;
import com.idgi.application.Main;
import com.idgi.core.IQuiz;
import com.idgi.core.NameableType;
import com.idgi.core.Student;
import com.idgi.core.TimedQuiz;
import com.idgi.core.User;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

import com.idgi.android.widget.CommentLayout;

import com.idgi.core.Comment;
import com.idgi.core.Lesson;
import com.idgi.Config;
import com.idgi.session.SessionData;
import com.idgi.android.recyclerview.adapters.ReplyAdapter;


import java.util.List;

/*
Activity that shows a single Lesson.
 */
public class LessonActivity extends DrawerActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private TextView txtNewComment;
    private ProgressBar pointProgressBar;
    private Lesson lesson;
    private Button quizButton;

    private PickQuizDialog quizTypes;

    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        lesson = SessionData.getCurrentLesson();

        String title = lesson == null ? getString(R.string.content_lesson_no_lesson_found_title) : lesson.getName();

        initializeWithTitle(title);

		initializePointsBar();

        if(lesson.hasQuiz()){
            quizButton = (Button) findViewById(R.id.content_lesson_quiz_button);
            if(quizButton != null) {
                quizButton.setVisibility(View.VISIBLE);
            }
        }

        initializeCommentView();
    }

    private void initializeCommentView() {
        txtNewComment = (TextView) findViewById(R.id.lesson_new_comment_field);
        comments = lesson.getDiscussion().getComments();

        recycler = (RecyclerView) findViewById(R.id.comment_list_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReplyAdapter(this, comments);
        recycler.setAdapter(adapter);
    }

    private void initializePointsBar() {
		User user = SessionData.getLoggedInUser();
        pointProgressBar = (ProgressBar) findViewById(R.id.content_lesson_point_progress);
		if (pointProgressBar != null) {
			if (user != null && user instanceof Student) {
				pointProgressBar.setMax(Config.MAX_POINTS_FOR_VIDEO);
				pointProgressBar.setVisibility(View.VISIBLE);

				Student student = (Student) user;
				pointProgressBar.setProgress(student.getPointsForVideo(SessionData.getCurrentVideo()));
			} else {
				pointProgressBar.setVisibility(View.GONE);
			}
		}
    }

    public void onToQuizButtonClick(View view) {
        IQuiz normalQuiz = lesson.getQuiz();
        SessionData.setCurrentQuiz(normalQuiz);

        quizTypes = new PickQuizDialog(this);

        quizTypes.show();
        quizTypes.getWindow().setGravity(Gravity.CENTER);
    }

    public void onCommentButtonClick(View view) {
		User user = SessionData.getLoggedInUser();
		if (user != null) {
			if (txtNewComment.getText().toString().length() != 0) {
				comments.add(0, new Comment(txtNewComment.getText().toString(), SessionData.getLoggedInUser().getName()));
				txtNewComment.setText("");
				adapter = new ReplyAdapter(this, comments);
				recycler.setAdapter(adapter);


				if (user.getType() == NameableType.STUDENT)
					((Student) user).addCommentToStatistics();

				Main.getDatabase().updateCurrentLesson();
			}
		} else {
			showRequireLoginDialog();
		}
    }

    public void onReplyButtonClick(View view) {
		if (SessionData.hasLoggedInUser())
			showReplyDialog(view);
		else
			showRequireLoginDialog();
    }

	private void showRequireLoginDialog() {
		new LoginRequiredDialog(this).show();
	}

	private void showReplyDialog(View childView) {
		final CommentLayout parentLayout = (CommentLayout) childView.getParent();

		Dialog replyDialog = CommentReplyDialog.createCommentReplyDialog(this, parentLayout);
		replyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				refreshComments();
			}
		});

		replyDialog.show();
	}

	@Subscribe
    public void onUpdatePoints(BusEvent busEvent) {
		if (busEvent.getEvent().equals(Event.POINTS_UPDATED)) {
			int value = (Integer) busEvent.getData();
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				ObjectAnimator animation = ObjectAnimator.ofInt(pointProgressBar, "progress", value);
				animation.setDuration(400);
				animation.setInterpolator(new DecelerateInterpolator());
				animation.start();
			} else {
				pointProgressBar.setProgress(value);
			}
		}
    }

    public void refreshComments(){
        adapter = new ReplyAdapter(this, comments);
        recycler.setAdapter(adapter);
    }

    @Subscribe
    public void onQuizTypeSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.QUIZ_TYPE_SELECTED) {
            IQuiz.Type quizType = (IQuiz.Type) busEvent.getData();

            if(quizType == IQuiz.Type.TIMED) {
                SessionData.setCurrentQuiz(TimedQuiz.from(SessionData.getCurrentQuiz()));
            } else if(quizType == IQuiz.Type.NORMAL) {
                SessionData.setCurrentQuiz(lesson.getQuiz());
            }

            startActivity(new Intent(this, QuizActivity.class));
            quizTypes.dismiss();
        }
    }

	@Subscribe
	public void onStartActivity(BusEvent busEvent) {
		if (busEvent.getEvent() == Event.START_ACTIVITY) {
			ActivityType type = (ActivityType) busEvent.getData();
			if (type == ActivityType.LOGIN)
				startActivity(new Intent(this, LoginActivity.class));
		}
	}
}

