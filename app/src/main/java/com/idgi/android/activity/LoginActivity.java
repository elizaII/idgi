package com.idgi.android.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.android.activities.extras.DrawerActivity;
import com.idgi.core.Account;
import com.idgi.services.FireDatabase;
import com.idgi.services.IDatabase;
import com.idgi.session.SessionData;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends DrawerActivity{

    private final IDatabase db = FireDatabase.getInstance();

    private EditText txtAccountName, txtPassword;

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
            startActivity(new Intent(this, MyCoursesActivity.class));
            finish();
        }
    }
}