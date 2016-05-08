package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.Widgets.CommentLayout;
import com.idgi.core.Comment;
import com.idgi.util.Storage;

public class CommentHolder extends ParentViewHolder {

    private TextView comment_text;
    private TextView comment_author;
    private TextView replies_count;
    private TextView reply_textField;
    private ImageView comment_imageView_profilePicture;
    private Comment comment;
    private CommentLayout view;

        public CommentHolder(View itemView) {
            super(itemView);
            comment_text = (TextView)itemView.findViewById(R.id.comment_text);
            //replies_count = (TextView)itemView.findViewById(R.id.replies_count);
            comment_author =(TextView) itemView.findViewById(R.id.comment_author);
            reply_textField =(TextView) itemView.findViewById(R.id.reply_textField);
            comment_imageView_profilePicture=(ImageView) itemView.findViewById(R.id.comment_imageView_profilePicture);
            this.view = (CommentLayout)itemView;
        }

        public void bind(Comment comment ) {
                view.setComment(comment);
            comment_text.setText(comment.getText());
            comment_author.setText(comment.getAuthor().getName());
//            replies_count.setText(Integer.toString(comment.getNbrOfReplies()));
            if (comment.getAuthor().getImage() != null) {
                comment_imageView_profilePicture.setImageDrawable(comment.getAuthor().getImage());
                comment_imageView_profilePicture.setBackground(null);
            }
            this.comment=comment;
        }

}
