package com.idgi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.idgi.core.User;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText nameText, phoneText, eMailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void createAccount(View view){
        User user = new User();
            user.setName(this.nameText.getText() != null ? nameText.getText().toString() : "Anonym");
            user.setEMail(this.eMailText.getText() != null ? eMailText.getText().toString() : "Anonym");
            user.setPhoneNumber(this.phoneText.getText() != null ? Integer.parseInt(phoneText.getText().toString()) : 000);
            startActivity(new Intent(CreateAccountActivity.this, StartActivity.class));

    }

}
