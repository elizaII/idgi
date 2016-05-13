package com.idgi.activities.recycleViews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.idgi.R;
import com.idgi.core.Comment;
import com.idgi.activities.recycleViews.viewHolders.CommentHolder;
import com.idgi.activities.recycleViews.viewHolders.ReplyHolder;

import java.util.ArrayList;
import java.util.List;

public class ReplyAdapter extends ExpandableRecyclerAdapter<CommentHolder, ReplyHolder> {
        private LayoutInflater inflater;

    public ReplyAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
            super(parentListItem);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public CommentHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View commentView = inflater.inflate(R.layout.lesson_listitem_comment, parentViewGroup, false);
            return new CommentHolder(commentView);
        }

        @Override
        public ReplyHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View replyView = inflater.inflate(R.layout.list_row_reply, childViewGroup, false);
            return new ReplyHolder(replyView);
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

    }