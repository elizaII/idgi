package com.idgi.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.activities.LessonActivity;
import com.idgi.R;
import com.idgi.core.Comment;
import com.idgi.core.Lesson;
import com.idgi.util.Storage;

/**
 * Created by Allex on 2016-05-08.
 */
public class CommentReplyDialog extends Dialog implements
            android.view.View.OnClickListener {

        private LessonActivity c;
        private Dialog d;
        private Button reply;
        private EditText reply_field;
    private Comment comment;

        public CommentReplyDialog(LessonActivity a, Comment comment) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = (LessonActivity)a;
            this.comment = comment;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.comment_reply_dialog);
            reply = (Button) findViewById(R.id.send_reply_button);
            reply_field = (EditText) findViewById(R.id.reply_field);
            reply.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.send_reply_button:
                    if (reply_field.getText().toString().length() !=0) {
                        comment.addReply(new Comment(reply_field.getText().toString(), Storage.getActiveUser()));
                        c.updateComments();
                    }
                    break;
                default:
                    break;
            }
            dismiss();
        }
}

