package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.Widgets.CreateDialog;
import com.idgi.core.Comment;

import java.util.ArrayList;

/**
 * Created by Allex on 2016-05-08.
 */
public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.ViewHolder> {

    private ArrayList<String> data;
    private LayoutInflater inflater;
    private CreateDialog dialog;
    private String string;

    public CreateAdapter(CreateDialog dialog, ArrayList<String> data, String string){
        this.data = data;
        inflater = LayoutInflater.from(dialog.getContext());
        this.dialog=dialog;
        this.string=string;
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
        dialog.selectItem(text, string);
       // reply_field.setText(string);

    }

}
}
