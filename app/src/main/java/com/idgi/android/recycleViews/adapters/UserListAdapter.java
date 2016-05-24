package com.idgi.android.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.idgi.R;
import com.idgi.core.User;

import java.util.List;

/**
 * Created by tove on 2016-05-21.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<User> users;

    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_user, parent, false);
        return new UserViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.setUpView(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends ChildViewHolder {

        private Context context;
        private TextView name;

        public UserViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;
            name = (TextView) itemView.findViewById(R.id.user_list_name);
        }

        public void setUpView(User user) {
            name.setText(user.getName());
        }
    }
}