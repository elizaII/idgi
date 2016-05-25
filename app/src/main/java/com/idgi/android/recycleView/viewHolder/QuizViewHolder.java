package com.idgi.android.recycleView.viewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;

/*
Used to display a Quiz in a list.
 */
public class QuizViewHolder extends NameableViewHolder {

    public static final int LAYOUT = R.layout.list_row;

    public TextView nameTextView;
    private Nameable nameable;

    public QuizViewHolder(View view){
        super(view);
        view.setOnClickListener(onViewClick);
    }

    private final View.OnClickListener onViewClick = new View.OnClickListener() {
        public void onClick(View view) {
            BusEvent nameableEvent = new BusEvent(Event.QUIZ_SELECTED, nameable);
            postToBus(nameableEvent);
        }
    };

    @Override
    public void bind(Nameable nameable) {
        nameTextView.setText(nameable.getName());
        this.nameable = nameable;
    }

    @Override
    public void initialize() {
        nameTextView = (TextView) findViewById(R.id.rowTextView);
    }

    public static NameableViewHolder create(LayoutInflater inflater, ViewGroup parent) {
        View view = getLayout(inflater, parent, LAYOUT);
        return new QuizViewHolder(view);
    }
}
