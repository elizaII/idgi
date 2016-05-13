package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.recycleViews.adapters.SelectNameableAdapter;
import com.idgi.util.Type;

import java.util.ArrayList;

public class SelectNameableDialog extends Dialog {
    private EditText txtCreateNew;
    private Type itemType;
    private SelectNameableAdapter adapter;
    private ArrayList<String> itemNames;

    private SelectNameableAdapter.ListChangeListener listener;

    public SelectNameableDialog(Context context, Type itemType, ArrayList<String> itemNames) {
        super(context);
        this.itemType = itemType;
        this.itemNames = itemNames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.selection_dialog);

        Button btnCreateNew = (Button) findViewById(R.id.create_new_button);
        txtCreateNew = (EditText) findViewById(R.id.create_new_editText);
        TextView txtTitle = (TextView) findViewById(R.id.title);
        txtTitle.setText("välj " + itemType);
        txtCreateNew.setHint("skriv namn för att skapa " + itemType);
        btnCreateNew.setOnClickListener(onCreateClick);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SelectNameableAdapter(getContext(), this.itemNames, itemType);
        recyclerView.setAdapter(adapter);
    }

    /* Triggers when you click an element in the list, like a School or Subject. */
    private final View.OnClickListener onCreateClick = new View.OnClickListener() {
        public void onClick(View view) {
            String name = txtCreateNew.getText().toString();
            listener.receiveItemData(name, itemType);
            dismiss();
        }
    };

    public void setListChangeListener(SelectNameableAdapter.ListChangeListener listener) {
        this.listener = listener;
		adapter.setListChangeListener(listener);
    }
}

