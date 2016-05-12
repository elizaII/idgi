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
    private ArrayList<Comment> data;
        private LayoutInflater mInflator;
    private Context context;

    public ReplyAdapter(Context context, @NonNull List<? extends ParentListItem> parentListItem) {
            super(parentListItem);
            mInflator = LayoutInflater.from(context);
        this.data= (ArrayList<Comment>)parentListItem;
        this.context = context;
        }

        // onCreate ...
        @Override
        public CommentHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View commentView = mInflator.inflate(R.layout.list_row_comment, parentViewGroup, false);
            return new CommentHolder(commentView);
        }

        @Override
        public ReplyHolder onCreateChildViewHolder(ViewGroup childViewGroup) {

            View replyView = mInflator.inflate(R.layout.list_row_reply, childViewGroup, false);
            return new ReplyHolder(replyView);
        }

        // onBind ...
        @Override
        public void onBindParentViewHolder(CommentHolder commentHolder, int position, ParentListItem parentListItem) {
            //commentHolder.bind(data.get(position));
            commentHolder.bind((Comment)parentListItem);

        }

        @Override
        public void onBindChildViewHolder(ReplyHolder replyHolder, int position, Object childListItem) {
                Comment comment = (Comment)childListItem;
                replyHolder.bind(comment);
        }

    }