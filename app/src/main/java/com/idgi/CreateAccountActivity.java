package com.idgi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameText, phoneText, eMailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initializeDrawer();

        nameText = (EditText) findViewById(R.id.nameText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        eMailText = (EditText) findViewById(R.id.eMailText);
        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this);
    }

    public void createAccount() {
        User user = new User();
        user.setName(this.nameText.getText().toString());
        user.setEMail(this.eMailText.getText().toString());

        String phone = phoneText.getText().toString();
        if (phone.equals("")) {
            user.setPhoneNumber(000);
        } else {
            user.setPhoneNumber(Integer.parseInt(phone));
        }

        Log.d("CreateAccountActivity", user.getName() + " " + user.getEMail());

        startActivity(new Intent(CreateAccountActivity.this, StartActivity.class));
    }
    private boolean formIsValid() {
        if (nameText.getText().equals("")) {
            return false;
        } else if (eMailText.getText().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createAccountButton) {
            if (formIsValid()) {
                createAccount();
            }
        }
    }
}
