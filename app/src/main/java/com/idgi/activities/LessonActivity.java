package com.idgi.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Config;
import com.idgi.util.Storage;
import com.idgi.recycleViews.adapters.ReplyAdapter;


import java.util.ArrayList;
import java.util.List;


public class LessonActivity extends AppCompatActivityWithDrawer implements YoutubeFragment.FragmentListener {


    Lesson currentLesson;
    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private List<Comment> commentList;
    private Database database = Database.getInstance();
    private TextView commentField;
    private ProgressBar pointProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initializeDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        pointProgressBar = (ProgressBar) findViewById(R.id.content_lesson_point_progress);
        pointProgressBar.setMax(Config.MAX_POINTS_FOR_VIDEO);
        pointProgressBar.setProgress(Storage.getActiveUser().getPointsForVideo(Storage.getCurrentVideo()));


        if(Storage.getCurrentLesson() != null) {
            currentLesson = Storage.getCurrentLesson();
            toolbar.setTitle(currentLesson.getName());
        } else {
            toolbar.setTitle("Kvadratrötter och potenser");
        }

        commentList = database.getComments(null);

        ArrayList<String> commentText = new ArrayList<>();

        manager = new LinearLayoutManager(this);
       // adapter = new CommentListAdapter(this, commentList);

        recycler = (RecyclerView) findViewById(R.id.comment_list_recycler_view);
        //recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        commentField = (TextView) findViewById(R.id.commentField);

        initializeDrawer();

        adapter = new ReplyAdapter(this, commentList);
        recycler.setAdapter(adapter);



    }


    public void onToQuizClick(View view) {
        /*
        //Retrieving the lesson's quiz
        Quiz quiz = currentLesson.getQuiz();

        //Setting it to the current quiz to be displayed in QuizActivity
        Storage.setCurrentQuiz(quiz);
        */

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("quiz_key", "Quiz123"); //Todo... replace with getCurrentQuiz()
        startActivity(intent);
    }

    public void onCommentButtonClick(View view) {
        database.addComment(new Comment(commentField.getText().toString(), Storage.getActiveUser()));
        commentList.add(0, new Comment(commentField.getText().toString(), Storage.getActiveUser()));
        commentField.setText("");
        updateComments();
    }

    public void onReplyButtonClick(View view) {
        final CommentLayout layout = (CommentLayout) view.getParent();
       /* // custom comment_reply_dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.comment_reply_dialog);
        dialog.setTitle("Svara på kommentar");

        // set the custom comment_reply_dialog components - text, image and button
        final TextView text = (TextView) dialog.findViewById(R.id.reply_textField);
        //text.setText("Android custom comment_reply_dialog example!");

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.getComment().addReply(new Comment(text.getText().toString(), Storage.getActiveUser()));
                adapter = new ReplyAdapter(context, commentList);
                recycler.setAdapter(adapter);
                dialog.dismiss();
            }
        });

        dialog.show();*/
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

