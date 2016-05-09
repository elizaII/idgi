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
        private Button reply;
        private EditText reply_field;
        private String string;
    private String school;
    private String subject;
        private TextView title;
    private CreateAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private ArrayList<String> data;

    private FireDatabase database;

        public CreateDialog(CreateLessonActivity a, String string, String school, String subject, ArrayList<String> data) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.string=string;
            this.data=data;
            this.school=school;
            this.subject=subject;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            database=FireDatabase.getInstance();
            setContentView(R.layout.selection_dialog);
            reply = (Button) findViewById(R.id.send_reply_button);
            reply_field = (EditText) findViewById(R.id.reply_field);
            title = (TextView) findViewById(R.id.title);
            title.setText("skapa ny " + string);
            reply_field.setHint("skriv in namn på " + string);
            reply.setOnClickListener(this);


            manager = new LinearLayoutManager(c);
            recyclerView = (RecyclerView) findViewById(R.id.param_list_recycler_view);
            recyclerView.setLayoutManager(manager);

            adapter = new CreateAdapter(this, this.data, string);
            recyclerView.setAdapter(adapter);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.send_reply_button && string.equals("skola")) {
                database.addSchool(new School(reply_field.getText().toString()));
            }
            if (v.getId() == R.id.send_reply_button && string.equals("ämne")) {
                database.getSchool(school).addSubject(new Subject(reply_field.getText().toString()));
            }
            if (v.getId() == R.id.send_reply_button && string.equals("kurs")) {
                Storage.getCurrentSubject().addCourse(new Course(reply_field.getText().toString()));
            }
            selectItem(reply_field.getText().toString(), string);
            dismiss();
        }

    public void selectItem(String text, String string) {
        c.selectItem(text, string);
        dismiss();

    }
}

