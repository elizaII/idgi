package com.idgi.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.core.Account;
import com.idgi.core.User;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameText, phoneText, eMailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameText = (EditText) findViewById(R.id.nameText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        eMailText = (EditText) findViewById(R.id.eMailText);
        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(this);
    }

    public void createAccount() {
        String name = this.nameText.getText().toString();
        String email = this.eMailText.getText().toString();

        // TODO implement actual account name
        String accountName = "test";

        // TODO implement actual password
        String password = "hej123";

        Account account = new Account(accountName, password);
        User user = new User(name);
        user.setEmail(email);
        account.setUser(user);

        String phone = phoneText.getText().toString();
        if (phone.isEmpty()) {
            user.setPhoneNumber("N/A");
        } else {
            user.setPhoneNumber(phone);
        }

        user.saveEmailToLocalStorage(this);

        Log.d("CreateAccountActivity", user.getName() + " " + user.getEmail());

        startActivity(new Intent(CreateAccountActivity.this, StartActivity.class));

    }
    private boolean formIsValid() {
        if (nameText.getText().toString().equals("")) {
            return false;
        } else if (eMailText.getText().toString().equals("")) {
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
