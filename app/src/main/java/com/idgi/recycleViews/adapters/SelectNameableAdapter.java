package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.util.Type;

import java.util.ArrayList;

public class SelectNameableAdapter extends RecyclerView.Adapter<SelectNameableAdapter.ViewHolder> {

    private ArrayList<String> itemNames;
    private LayoutInflater inflater;
    private Type itemType;

    private ListChangeListener listener;

    public SelectNameableAdapter(Context context, ArrayList<String> itemNames, Type itemType){
        this.itemNames = itemNames;
        inflater = LayoutInflater.from(context);
        this.itemType = itemType;
        }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
        }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(itemNames.get(position));
        }

    @Override
    public int getItemCount() {
        return itemNames.size();
        }

	public interface ListChangeListener {
		void receiveItemData(String text, Type type);
	}

	public void setListChangeListener(ListChangeListener listener) {
		this.listener = listener;
	}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            textView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String itemName = textView.getText().toString();
            listener.receiveItemData(itemName, itemType);
        }
    }
}
