package com.idgi.android.recyclerview.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.idgi.R;
import com.idgi.android.widget.CommentLayout;
import com.idgi.application.Main;
import com.idgi.core.Comment;
import com.idgi.core.NameableType;
import com.idgi.core.Student;
import com.idgi.core.User;

import java.util.List;
import java.util.Locale;

/*
Used to list replies beneath top-level comments.
 */
public class ReplyAdapter extends ExpandableRecyclerAdapter<ReplyAdapter.CommentHolder, ReplyAdapter.ReplyHolder> {
    private LayoutInflater inflater;
    private Context context;

    public ReplyAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
            super(parentListItem);
        this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public CommentHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View commentView = inflater.inflate(R.layout.lesson_listitem_comment, parentViewGroup, false);
            return new CommentHolder(context, commentView);
        }

        @Override
        public ReplyHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View replyView = inflater.inflate(R.layout.list_row_reply, childViewGroup, false);
            return new ReplyHolder(context, replyView);
        }

        @Override
        public void onBindParentViewHolder(CommentHolder commentHolder, int position, ParentListItem parentListItem) {
			Comment comment = (Comment) parentListItem;
            commentHolder.bind(comment);
            //commentHolder.bind((Comment)parentListItem);

        }

        @Override
        public void onBindChildViewHolder(ReplyHolder replyHolder, int position, Object childListItem) {
                Comment comment = (Comment)childListItem;
                replyHolder.bind(comment);
        }

    public static class ReplyHolder extends ChildViewHolder {
        private TextView txtReply;
        private TextView txtAuthor;
        private ImageView imgProfilePicture;
        private Context context;

        public ReplyHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            txtReply = (TextView)itemView.findViewById(R.id.reply_text);
            txtAuthor =(TextView) itemView.findViewById(R.id.reply_author);
            imgProfilePicture = (ImageView) itemView.findViewById(R.id.reply_imageView_profilePicture);
        }

        public void bind(Comment reply) {
            txtReply.setText(reply.getText());
            txtAuthor.setText(reply.getAuthorAccountName());
            User user = Main.getDatabase().getUserByAccountName(reply.getAuthorAccountName());

            if (user.getProfilePicture() != null) {
                imgProfilePicture.setImageDrawable(new BitmapDrawable(context.getResources(), user.getProfilePicture()));
                imgProfilePicture.setBackground(null);
            }
        }
    }

    public static class CommentHolder extends ParentViewHolder {

        private TextView txtComment, txtAuthor, txtReplyAmount;
        private ImageView imgProfilePicture;
        private View replyInfoLayout;
        private CommentLayout view;
        private Context context;

        public CommentHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;

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
            User user = Main.getDatabase().getUserByAccountName(comment.getAuthorAccountName());
            updateAuthor(user);
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
                imgProfilePicture.setImageDrawable(new BitmapDrawable(context.getResources(), author.getProfilePicture()));
                imgProfilePicture.setBackground(null);
            }
        }

        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return false;
        }

    }

}