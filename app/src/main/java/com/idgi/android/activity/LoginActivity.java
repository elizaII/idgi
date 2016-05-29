package com.idgi.android.activity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.idgi.R;
import com.idgi.application.Main;
import com.idgi.core.Account;
import com.idgi.service.FireDatabase;
import com.idgi.service.IDatabase;
import com.idgi.session.SessionData;

/*
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends DrawerActivity{

    private final IDatabase db = FireDatabase.getInstance();

    private EditText txtAccountName, txtPassword;
    private TextView txtCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String title = getResources().getString(R.string.title_activity_login);
        super.initializeWithTitle(title);

        initialize();
    }

    private void initialize() {
        txtAccountName = (EditText) findViewById(R.id.login_txt_account_name);
        txtPassword = (EditText) findViewById(R.id.login_txt_account_password);
        txtCreateAccount = (TextView) findViewById(R.id.login_txt_create_account);

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });
    }

    private String getAccountName() {
        return txtAccountName.getText().toString();
    }

    private String getPassword() {
        return txtPassword.getText().toString();
    }

    public void onLoginButtonClick(View view) {
        Account account = db.getAccount(getAccountName(), getPassword());
        if (account != null) {
            SessionData.setLoggedInAccount(account);
            Main.getDatabase().downloadProfilePicture(this, SessionData.getLoggedInAccount());
            startActivity(new Intent(this, StartActivity.class));
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.login_wrong_account_or_password), Toast.LENGTH_SHORT).show();
        }
    }
}