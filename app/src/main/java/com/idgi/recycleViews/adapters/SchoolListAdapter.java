package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.SubjectListActivity;

import java.util.ArrayList;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;

    public SchoolListAdapter(Context context, ArrayList<String> data){
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
        holder.schoolTextView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView schoolTextView;

        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            schoolTextView =(TextView) v.findViewById(R.id.rowTextView);
        }

        public void onClick(View v){
            String s = schoolTextView.getText().toString();
            System.out.println(s);
            Intent intent = new Intent(v.getContext(), SubjectListActivity.class);
            intent.putExtra("schoolName", s);
            v.getContext().startActivity(intent);
        }

    }


}
