package com.idgi.activities.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.SubjectListActivity;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;
import com.idgi.util.Nameable;

import java.util.ArrayList;

/**
 * Created by Emil on 12/05/2016.
 */
public class SearchableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Nameable> data;

    private final int SCHOOL = 0, SUBJECT = 1, COURSE = 2, LESSON = 3;

    private LayoutInflater inflater;

    public SearchableAdapter(Context context, ArrayList<Nameable>data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof School) {
            return SCHOOL;
        } else if (data.get(position) instanceof Subject) {
            return SUBJECT;
        }
        else if (data.get(position) instanceof Subject) {
            return COURSE;
        }
        else if (data.get(position) instanceof Subject) {
            return LESSON;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case SCHOOL:
                View v1 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case SUBJECT:
                View v2 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            case COURSE:
                View v3 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder3(v3);
                break;
            case LESSON:
                View v4 = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder4(v4);
                break;
            default:
                View v = inflater.inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder1(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case SCHOOL:
                ViewHolder1 holder1 = (ViewHolder1) holder;
                holder1.searchTextView.setText(data.get(position).getName());
                break;
            case SUBJECT:
                ViewHolder2 holder2 = (ViewHolder2) holder;
                holder2.searchTextView.setText(data.get(position).getName());
                break;
            case COURSE:
                ViewHolder3 holder3 = (ViewHolder3) holder;
                holder3.searchTextView.setText(data.get(position).getName());
                break;
            case LESSON:
                ViewHolder4 holder4 = (ViewHolder4) holder;
                holder4.searchTextView.setText(data.get(position).getName());
                break;
            default:

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolder1(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            //TODO implement so that navigation to selected item works
        }
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolder2(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            //TODO implement so that navigation to selected item works
        }
    }

    public static class ViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolder3(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            //TODO implement so that navigation to selected item works
        }
    }

    public static class ViewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolder4(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            //TODO implement so that navigation to selected item works
        }
    }
}