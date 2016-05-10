package com.idgi.Widgets;

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
import com.idgi.core.Course;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.recycleViews.adapters.CreateAdapter;

import java.util.ArrayList;

public class CreateDialog extends Dialog implements
            View.OnClickListener {

    private CreateLessonActivity activity;
    private Button btnCreateNew;
    private EditText txtCreateNew;
    private CreateAdapter.Type type;
    private TextView title;
    private CreateAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private ArrayList<String> data;


        public CreateDialog(CreateLessonActivity activity, CreateAdapter.Type type, ArrayList<String> data) {
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
            btnCreateNew.setOnClickListener(this);

            manager = new LinearLayoutManager(activity);
            recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
            recyclerView.setLayoutManager(manager);

            adapter = new CreateAdapter(this, this.data, type);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.send_reply_button) {
                String name = txtCreateNew.getText().toString();
                switch(type) {
                    case SCHOOL:
                        activity.setSchool(new School(name));
                        break;
                    case SUBJECT:
                        activity.setSubject(new Subject(name));
                        break;
                    case COURSE:
                        activity.setCourse(new Course(name));
                    default:
                        break;
                }
            }

            selectItem(txtCreateNew.getText().toString(), type);
            dismiss();
        }

    public void selectItem(String text, CreateAdapter.Type type) {
        activity.selectItem(text, type);
        dismiss();
    }
}

