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

/**
 * Created by Allex on 2016-05-07.
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
