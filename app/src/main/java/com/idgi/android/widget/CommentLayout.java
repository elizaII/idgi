package com.idgi.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.idgi.core.Comment;

/*
Lets us access the Comment associated with a list-item representing a Comment.
 */
public class CommentLayout extends RelativeLayout {


    private Comment comment;

    public CommentLayout(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
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
