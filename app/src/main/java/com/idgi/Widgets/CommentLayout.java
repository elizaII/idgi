package com.idgi.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Comment;

import java.util.List;

public class CommentLayout extends RelativeLayout {


    private Comment comment;
    private TextView txtReplyAmount;

    public CommentLayout(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

        txtReplyAmount = (TextView) findViewById(R.id.lesson_listitem_comment_reply_amount_text);
    }

    public CommentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CommentLayout(Context context) {
        super(context);
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

}
