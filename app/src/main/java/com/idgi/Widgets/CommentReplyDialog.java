package com.idgi.Widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.activities.LessonActivity;
import com.idgi.R;
import com.idgi.core.Comment;
import com.idgi.session.SessionData;

public class CommentReplyDialog extends Dialog implements
            android.view.View.OnClickListener {

        private LessonActivity activity;
        private Dialog dialog;
        private Button reply;
        private EditText reply_field;
    private Comment comment;

        public CommentReplyDialog(LessonActivity activity, Comment comment) {
            super(activity);
            // TODO Auto-generated constructor stub
            this.activity = activity;
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
                        comment.addReply(new Comment(reply_field.getText().toString(), SessionData.getLoggedInUser()));
                        //activity.updateComments();
                    }
                    break;
                default:
                    break;
            }
            dismiss();
        }
}

