package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.core.Comment;

public class CommentHolder extends ParentViewHolder {

        private TextView commentTextView;

        public CommentHolder(View itemView) {
            super(itemView);
            commentTextView = (TextView)itemView.findViewById(R.id.comment_text);
        }

        public void bind(Comment comment ) {
           commentTextView.setText(comment.getText());
        }
    }