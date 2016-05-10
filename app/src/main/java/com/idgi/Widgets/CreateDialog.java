package com.idgi.Widgets;

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
import com.idgi.activities.CreateLessonActivity;
import com.idgi.activities.LessonActivity;
import com.idgi.core.Comment;
import com.idgi.core.Course;
import com.idgi.core.School;
import com.idgi.core.Subject;
import com.idgi.recycleViews.adapters.CreateAdapter;
import com.idgi.services.FireDatabase;
import com.idgi.util.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allex on 2016-05-08.
 */
public class CreateDialog extends Dialog implements
            View.OnClickListener {

    private CreateLessonActivity c;
    private Button create_new_button;
    private EditText create_new_editText;
    private String string;
    private TextView title;
    private CreateAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private ArrayList<String> data;


        public CreateDialog(CreateLessonActivity a, String string, ArrayList<String> data) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.string=string;
            this.data=data;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.selection_dialog);
            create_new_button = (Button) findViewById(R.id.create_new_button);
            create_new_editText = (EditText) findViewById(R.id.create_new_editText);
            title = (TextView) findViewById(R.id.title);
            title.setText("välj " + string);
            create_new_editText.setHint("skriv namn för att skapa " + string);
            create_new_button.setOnClickListener(this);

            manager = new LinearLayoutManager(c);
            recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
            recyclerView.setLayoutManager(manager);

            adapter = new CreateAdapter(this, this.data, string);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.send_reply_button && string.equals("skola")) {
                c.setSchool(new School(create_new_editText.getText().toString()));
            }
            if (v.getId() == R.id.send_reply_button && string.equals("ämne")) {
                c.setSubject(new Subject(create_new_editText.getText().toString()));
            }
            if (v.getId() == R.id.send_reply_button && string.equals("kurs")) {
                c.setCourse(new Course(create_new_editText.getText().toString()));
            }
            selectItem(create_new_editText.getText().toString(), string);
            dismiss();
        }

    public void selectItem(String text, String string) {
        c.selectItem(text, string);
        dismiss();
    }
}

