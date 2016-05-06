package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.idgi.R;
import com.idgi.core.Comment;
import com.idgi.recycleViews.viewHolders.CommentHolder;
import com.idgi.recycleViews.viewHolders.ReplyHolder;

import java.util.List;

public class ReplyAdapter extends ExpandableRecyclerAdapter<CommentHolder, ReplyHolder> {

        private LayoutInflater mInflator;

    public ReplyAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
            super(parentListItem);
            mInflator = LayoutInflater.from(context);
        }

        // onCreate ...
        @Override
        public CommentHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View commentView = mInflator.inflate(R.layout.list_row_comment, parentViewGroup, false);
            return new CommentHolder(commentView);
        }

        @Override
        public ReplyHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View replyView = mInflator.inflate(R.layout.list_row_comment, childViewGroup, false);
            return new ReplyHolder(replyView);
        }

        // onBind ...
        @Override
        public void onBindParentViewHolder(CommentHolder commentHolder, int position, ParentListItem parentListItem) {
            Comment comment = (Comment) parentListItem;
            commentHolder.bind(comment);
        }

        @Override
        public void onBindChildViewHolder(ReplyHolder replyHolder, int position, Object childListItem) {
            Comment comment = (Comment) childListItem;
            replyHolder.bind(comment);
        }
    }