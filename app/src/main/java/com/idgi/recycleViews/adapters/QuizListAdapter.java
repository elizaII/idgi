package com.idgi.recycleViews.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Lesson;
import com.idgi.event.NameableSelectionBus;

import java.util.List;

/**
 * Created by Emil on 17/05/2016.
 */

public class QuizListAdapter extends NameableAdapter<QuizListAdapter.ViewHolder> {

    public QuizListAdapter(Context context, List<Lesson> lessons){
        super(context, lessons);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, getBus());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.quizTextView.setText(nameables.get(position).getName());
    }

    public void addListener(NameableSelectionBus.Listener listener) {
        getBus().addListener(listener);
    }

    public static class ViewHolder extends NameableAdapter.ViewHolder implements View.OnClickListener{

        public TextView quizTextView;

        public ViewHolder(View view, NameableSelectionBus bus){
            super(view, bus);
            view.setOnClickListener(this);
            quizTextView = (TextView) view.findViewById(R.id.rowTextView);
        }

        public void onClick(View view){
            String quizName = quizTextView.getText().toString();
            broadcastSelection(quizName);
        }
    }
}

