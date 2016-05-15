package com.idgi.activities;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.dialogs.PickQuizDialog;
import com.idgi.activities.extras.ActivityType;
import com.idgi.activities.extras.DialogFactory;
import com.idgi.core.IQuiz;
import com.idgi.core.TimedQuiz;
import com.idgi.fragments.YoutubeFragment;

import com.idgi.Widgets.CommentLayout;

import com.idgi.core.Comment;
import com.idgi.core.Lesson;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.util.Config;
import com.idgi.session.SessionData;
import com.idgi.recycleViews.adapters.ReplyAdapter;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Time;
import java.util.List;

public class LessonActivity extends DrawerActivity implements YoutubeFragment.FragmentListener, PropertyChangeListener {

    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private TextView txtNewComment;
    private ProgressBar pointProgressBar;
    private Lesson lesson;

    private List<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        lesson = SessionData.getCurrentLesson();

        String title = lesson == null ? getString(R.string.content_lesson_no_lesson_found_title) : lesson.getName();

        initializeWithTitle(title);

        if (SessionData.hasLoggedInUser())
            initializePointsBar();

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
        pointProgressBar = (ProgressBar) findViewById(R.id.content_lesson_point_progress);
		if (pointProgressBar != null) {
			pointProgressBar.setMax(Config.MAX_POINTS_FOR_VIDEO);
			pointProgressBar.setProgress(SessionData.getLoggedInUser().getPointsForVideo(SessionData.getCurrentVideo()));
		}
    }


    public void onToQuizClick(View view) {
        PickQuizDialog quizTypes = new PickQuizDialog(this);

        //Listens to the dialog
        quizTypes.addPropertyChangeListener(this);

        quizTypes.show();
        quizTypes.getWindow().setGravity(Gravity.CENTER);
    }

    public void onCommentButtonClick(View view) {
		if (SessionData.hasLoggedInUser()) {
			if (txtNewComment.getText().toString().length() != 0) {
				comments.add(0, new Comment(txtNewComment.getText().toString(), SessionData.getLoggedInUser()));
				txtNewComment.setText("");
				refreshComments();
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
		DialogFactory.createLoginRequiredDialog(this).show();
	}

	private void showReplyDialog(View childView) {
		final CommentLayout parentLayout = (CommentLayout) childView.getParent();

		Dialog replyDialog = DialogFactory.createCommentReplyDialog(this, parentLayout);
		replyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				refreshComments();
			}
		});

		replyDialog.show();
	}

    @Override
    public void onUpdatePoints(int value) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator animation = ObjectAnimator.ofInt(pointProgressBar, "progress", value);
            animation.setDuration(400);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else {
            pointProgressBar.setProgress(value);
        }
    }
   public void refreshComments(){
	   adapter = new ReplyAdapter(this, comments);
	   recycler.setAdapter(adapter);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equals("PickQuizDialog")) {
            if(event.getNewValue().equals("normal")){
                //Do nothing...
            } else if(event.getNewValue().equals("timed")) {
                IQuiz normalQuiz = lesson.getQuiz();
                lesson.setQuiz(new TimedQuiz(normalQuiz, 5000));
                TimedQuiz timedQuiz = (TimedQuiz) lesson.getQuiz();

                //Since Json can't recognise parameters we have to manually set
                //the time after creating the TimedQuiz too
                //Todo... better solution for Json's unrecognising of properties
                timedQuiz.setTime(6000);
            }

            startActivity(ActivityType.QUIZ);
        }
    }
}

