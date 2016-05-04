package com.idgi.Widgets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idgi.LessonActivity;
import com.idgi.R;
import com.idgi.SubjectListActivity;
import com.idgi.core.Comment;

import java.util.ArrayList;

/**
 * Created by Allex on 2016-05-04.
 */
public class CommentListAdapter  extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private ArrayList<Comment> data;
    private LayoutInflater inflater;

    public CommentListAdapter(Context context, ArrayList<Comment> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.list_row_comment, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.comment_text.setText(data.get(position).getText());
        holder.comment_author.setText(data.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView comment_text;
        public TextView comment_author;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            comment_text =(TextView) v.findViewById(R.id.comment_text);
            comment_author =(TextView) v.findViewById(R.id.comment_author);
        }

        public void onClick(View v){
           /* String s = subjectTextView.getText().toString();
            System.out.println(s);
            Intent intent = new Intent(v.getContext(), LessonActivity.class);
            intent.putExtra("subjectName", s);
            v.getContext().startActivity(intent);*/
        }

    }

}



