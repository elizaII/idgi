package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Comment;

import java.util.ArrayList;

public class CommentListAdapter  extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {
    private ArrayList<Comment> data;
    private LayoutInflater inflater;

    public CommentListAdapter(Context context, ArrayList<Comment> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_comment, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.comment_text.setText(data.get(position).getText());
        holder.comment_author.setText(data.get(position).getAuthor().getName());
        if (data.get(position).getAuthor().getImage() != null) {
            holder.comment_imageView_profilePicture.setImageDrawable(data.get(position).getAuthor().getImage());
            holder.comment_imageView_profilePicture.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView comment_text;
        private TextView comment_author;
        private ImageView comment_imageView_profilePicture;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            comment_text =(TextView) v.findViewById(R.id.comment_text);
            comment_author =(TextView) v.findViewById(R.id.comment_author);
            comment_imageView_profilePicture=(ImageView) v.findViewById(R.id.comment_imageView_profilePicture);
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



