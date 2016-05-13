package com.idgi.activities.recycleViews.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.Comment;

public class ReplyHolder extends ChildViewHolder {

    private TextView txtReply;
    private TextView txtAuthor;
    private ImageView imgProfilePicture;

    public ReplyHolder(View itemView) {
        super(itemView);
        txtReply = (TextView)itemView.findViewById(R.id.reply_text);
        txtAuthor =(TextView) itemView.findViewById(R.id.reply_author);
        imgProfilePicture = (ImageView) itemView.findViewById(R.id.reply_imageView_profilePicture);
    }

    public void bind(Comment reply ) {
        txtReply.setText(reply.getText());
        txtAuthor.setText(reply.getAuthor().getName());
        if (reply.getAuthor().getImage() != null) {
            imgProfilePicture.setImageDrawable(reply.getAuthor().getImage());
            imgProfilePicture.setBackground(null);
        }
    }
}

