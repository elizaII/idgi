package com.idgi.activities.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.idgi.R;
import com.idgi.activities.CreateLessonActivity;
import com.idgi.activities.recycleViews.adapters.CreateAdapter;
import com.idgi.util.Type;

import java.util.ArrayList;

public class CreateDialog extends Dialog {

    private CreateLessonActivity activity;
    private Button btnCreateNew;
    private EditText txtCreateNew;
    private Type type;
    private TextView title;
    private CreateAdapter adapter;
    private ArrayList<String> data;


        public CreateDialog(CreateLessonActivity activity, Type type, ArrayList<String> data) {
            super(activity);

            this.activity = activity;
            this.type = type;
            this.data = data;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.selection_dialog);

            btnCreateNew = (Button) findViewById(R.id.create_new_button);
            txtCreateNew = (EditText) findViewById(R.id.create_new_editText);
            title = (TextView) findViewById(R.id.title);
            title.setText("välj " + type);
            txtCreateNew.setHint("skriv namn för att skapa " + type);
            btnCreateNew.setOnClickListener(onCreateClick);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));

            adapter = new CreateAdapter(this, this.data, type);
            recyclerView.setAdapter(adapter);

        }

    /* Triggers when you click an element in the list, like a School or Subject. */
    private final View.OnClickListener onCreateClick = new View.OnClickListener() {
        public void onClick(View view) {
            String name = txtCreateNew.getText().toString();

            selectItem(name, type);
            dismiss();
        }
    };

    public void selectItem(String text, Type type) {
        activity.selectItem(text, type);
        dismiss();
    }
}

