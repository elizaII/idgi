package com.idgi.activities.recycleViews.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Comment;

public class ReplyHolder extends ChildViewHolder {

    private TextView reply_text;
    private TextView reply_author;
    private ImageView reply_imageView_profilePicture;

    public ReplyHolder(View itemView) {
        super(itemView);
        reply_text = (TextView)itemView.findViewById(R.id.reply_text);
        reply_author =(TextView) itemView.findViewById(R.id.reply_author);
        reply_imageView_profilePicture=(ImageView) itemView.findViewById(R.id.reply_imageView_profilePicture);
    }

    public void bind(Comment reply ) {
        reply_text.setText(reply.getText());
        reply_author.setText(reply.getAuthor().getName());
        if (reply.getAuthor().getImage() != null) {
            reply_imageView_profilePicture.setImageDrawable(reply.getAuthor().getImage());
            reply_imageView_profilePicture.setBackground(null);
        }
    }
}

