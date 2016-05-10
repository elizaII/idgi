package com.idgi.recycleViews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.CreateDialog;

import java.util.ArrayList;

public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {

    public enum Type {
        SCHOOL, SUBJECT, COURSE;

        /* Returns a readable version of the type: School, Subject or Course */
        public String toString() {
            switch (this) {
                case SCHOOL:
                    return "skola";
                case SUBJECT:
                    return "ämne";
                case COURSE:
                    return "kurs";
            }

            return null;
        }
    }

    private ArrayList<String> data;
    private LayoutInflater inflater;
    private CreateDialog dialog;
    private Type type;

    public CreateAdapter(CreateDialog dialog, ArrayList<String> data, Type type){
        this.data = data;
        inflater = LayoutInflater.from(dialog.getContext());
        this.dialog = dialog;
        this.type = type;
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);

        }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
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
            String text = textView.getText().toString();
            dialog.selectItem(text, type);
        }
    }
}