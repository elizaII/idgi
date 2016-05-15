package com.idgi.recycleViews.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.Widgets.CommentLayout;
import com.idgi.core.Comment;
import com.idgi.core.User;

import java.util.Locale;

public class CommentHolder extends ParentViewHolder {

    private TextView txtComment, txtAuthor, txtReplyAmount;
    private ImageView imgProfilePicture;
	private View replyInfoLayout;
    private CommentLayout view;

        public CommentHolder(View itemView) {
            super(itemView);

            txtComment = (TextView) load(R.id.lesson_listitem_comment_text);
			txtReplyAmount = (TextView) load(R.id.lesson_listitem_comment_reply_amount_text);
			txtAuthor = (TextView) load(R.id.comment_author);
            imgProfilePicture = (ImageView) load(R.id.lesson_listitem_comment_image_profile_picture);

			replyInfoLayout = load(R.id.lesson_listitem_comment_view_to_expand_replies);
			replyInfoLayout.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					System.out.println("We in it!");

					if (isExpanded())
						collapseView();
					else
						expandView();
				}
			});

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
		txtComment.setText(comment.getText());

		String replyAmount = String.format(Locale.ENGLISH, "%d", comment.getNumberOfReplies());
		txtReplyAmount.setText(replyAmount);
	}

	private void updateAuthor(User author) {
		String authorName = author == null ? "N/A" : author.getName();
		txtAuthor.setText(authorName);

		if (author != null && author.getProfilePicture() != null) {
			imgProfilePicture.setImageDrawable(author.getProfilePicture());
			imgProfilePicture.setBackground(null);
		}
	}

	@Override
	public boolean shouldItemViewClickToggleExpansion() {
		return false;
	}

}
