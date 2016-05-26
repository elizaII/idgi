package com.idgi.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.idgi.R;
import com.idgi.android.widget.CommentLayout;
import com.idgi.core.Comment;
import com.idgi.session.SessionData;

/**
 * Created by tove on 2016-05-26.
 */
public class CommentReplyDialog {

    public static Dialog createCommentReplyDialog(final Context context, final CommentLayout parentLayout) {
        final Dialog dialog = new Dialog(context);

        View contentView = getContentView(context, R.layout.dialog_reply_to_comment);
        dialog.setContentView(contentView);

        final Comment parentComment = parentLayout.getComment();

        Button btnReply = (Button) contentView.findViewById(R.id.dialog_reply_to_comment_button_send_reply);
        final EditText txtReply = (EditText) contentView.findViewById(R.id.dialog_reply_to_comment_text_reply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = txtReply.getText().toString();
                if (message.length() > 0)
                    parentComment.addReply(message, SessionData.getLoggedInUser());
                //parentLayout

                dialog.dismiss();
            }
        });

        setStandardLayout(dialog);
        return dialog;
    }

    private static void setStandardLayout(Dialog dialog) {
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private static View getContentView(Context context, int resource) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return inflater.inflate(resource, null);
    }
}
