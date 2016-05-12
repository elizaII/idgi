package com.idgi.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.fragments.YoutubeFragment;

import com.idgi.Widgets.CommentLayout;

import com.idgi.Widgets.CommentReplyDialog;
import com.idgi.core.Comment;
import com.idgi.core.Lesson;
import com.idgi.services.MockData;
import com.idgi.activities.extras.AppCompatActivityWithDrawer;
import com.idgi.util.Config;
import com.idgi.session.SessionData;
import com.idgi.activities.recycleViews.adapters.ReplyAdapter;


import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivityWithDrawer implements YoutubeFragment.FragmentListener {

    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private List<Comment> commentList;
    private MockData database = MockData.getInstance();
    private TextView commentField;
    private ProgressBar pointProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Lesson lesson = SessionData.getCurrentLesson();

        String title = lesson == null ? getString(R.string.content_lesson_no_lesson_found_title) : lesson.getName();

        initializeWithTitle(title);

        if (SessionData.hasLoggedInUser()) {
            pointProgressBar = (ProgressBar) findViewById(R.id.content_lesson_point_progress);
            pointProgressBar.setMax(Config.MAX_POINTS_FOR_VIDEO);
            pointProgressBar.setProgress(SessionData.getLoggedInUser().getPointsForVideo(SessionData.getCurrentVideo()));
        }

        commentList = database.getComments(null);
        ArrayList<String> commentText = new ArrayList<>();

        manager = new LinearLayoutManager(this);

        recycler = (RecyclerView) findViewById(R.id.comment_list_recycler_view);
        recycler.setLayoutManager(manager);

        commentField = (TextView) findViewById(R.id.commentField);
        adapter = new ReplyAdapter(this, commentList);
        recycler.setAdapter(adapter);
    }


    public void onToQuizClick(View view) {
        startActivity(new Intent(this, QuizActivity.class));
    }

    public void onCommentButtonClick(View view) {
        if(commentField.getText().toString().length() != 0) {
            database.addComment(new Comment(commentField.getText().toString(), SessionData.getLoggedInUser()));
            commentList.add(0, new Comment(commentField.getText().toString(), SessionData.getLoggedInUser()));
            commentField.setText("");
            updateComments();
        }
    }

    public void onReplyButtonClick(View view) {
        final CommentLayout layout = (CommentLayout) view.getParent();
        CommentReplyDialog dialog=new CommentReplyDialog(this, layout.getComment());
        dialog.show();

    }

    @Override
    public void updatePoints(int value) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            ObjectAnimator animation = ObjectAnimator.ofInt(pointProgressBar, "progress", value);
            animation.setDuration(400);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else {
            pointProgressBar.setProgress(value);
        }
    }
    public void updateComments(){
        adapter = new ReplyAdapter(this, commentList);
        recycler.setAdapter(adapter);

    }

}

