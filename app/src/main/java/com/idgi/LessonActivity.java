package com.idgi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.idgi.Widgets.CommentListAdapter;
import com.idgi.core.Comment;
import com.idgi.core.Lesson;
import com.idgi.services.Database;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Storage;

import java.util.ArrayList;


public class LessonActivity extends AppCompatActivityWithDrawer {


    Lesson currentLesson;
    private RecyclerView.Adapter adapter;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Comment> commentList = new ArrayList<>();
    private Database database = Database.getInstance();
    private TextView commentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initializeDrawer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        if(Storage.getCurrentLesson() != null) {
            currentLesson = Storage.getCurrentLesson();
            toolbar.setTitle(currentLesson.getName());
        } else {
            toolbar.setTitle("Kvadratrötter och potenser");
        }

        commentList= (ArrayList<Comment>) database.getComments();

        ArrayList<String> commentText = new ArrayList<>();

        manager = new LinearLayoutManager(this);
        adapter = new CommentListAdapter(this, commentList);

        recycler = (RecyclerView) findViewById(R.id.comment_list_recycler_view);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(manager);

        commentField = (TextView) findViewById(R.id.commentField);

        initializeDrawer();
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
        database.addComment(new Comment(commentField.getText().toString(), "No name"));
        commentField.setText("");
        adapter.notifyDataSetChanged();

    }


}
