package com.idgi.activities.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Course;

import java.util.ArrayList;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder>{


    private ArrayList<Course> data;
    private LayoutInflater inflater;



    public MyCoursesAdapter(Context context, ArrayList<Course> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_course, parent, false);

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            textView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
         //   String text = textView.getText().toString();
        }
    }
}
