package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Comment;

public class ReplyHolder extends ChildViewHolder {

    private TextView commentTextView;

    public ReplyHolder(View itemView) {
        super(itemView);
        commentTextView = (TextView)itemView.findViewById(R.id.comment_text);
    }

    public void bind(Comment comment ) {
        commentTextView.setText(comment.getText());
    }
}

