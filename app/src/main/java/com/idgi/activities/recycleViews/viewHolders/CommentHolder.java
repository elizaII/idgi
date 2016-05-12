package com.idgi.activities.recycleViews.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.Widgets.CommentLayout;
import com.idgi.core.Comment;
import com.idgi.core.User;

public class CommentHolder extends ParentViewHolder {

    private TextView txtComment, txtAuthor, txtSeeReplies;
    private ImageView imgProfilePicture;
    private CommentLayout view;

        public CommentHolder(View itemView) {
            super(itemView);

            txtComment = (TextView) load(R.id.comment_text);
			txtSeeReplies = (TextView) load(R.id.see_replies_text);
			txtAuthor = (TextView) load(R.id.comment_author);
            imgProfilePicture = (ImageView) load(R.id.comment_imageView_profilePicture);

            this.view = (CommentLayout)itemView;
        }

	private View load(int id) {
		return itemView.findViewById(id);
	}

	public void bind(Comment comment) {
		updateComment(comment);
		updateAuthor(comment.getAuthor());
	}

	private void updateComment(Comment comment) {
		view.setComment(comment);
		txtComment.setText(txtComment.getText());

		txtSeeReplies.setText("Se svar: " + comment.getNbrOfReplies() + "st");
	}

	private void updateAuthor(User author) {
		String authorName = author == null ? "N/A" : author.getName();
		txtAuthor.setText(authorName);


		if (author != null && author.getImage() != null) {
			imgProfilePicture.setImageDrawable(author.getImage());
			imgProfilePicture.setBackground(null);
		}
	}

}
