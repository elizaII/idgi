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
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

import java.util.ArrayList;

/**
 * Created by Emil on 12/05/2016.
 */
public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public SearchableAdapter(Context context, ArrayList<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.searchTextView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            searchTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            //TODO implement so that navigation to selected item works
        }
    }

}