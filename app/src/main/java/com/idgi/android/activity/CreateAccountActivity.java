package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.idgi.R;
import com.idgi.core.Account;
import com.idgi.core.StudentUser;
import com.idgi.core.TeacherUser;
import com.idgi.core.User;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;

/*
Activity for creating either a Student or a Teacher account.
 */
public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameText;
    private EditText phoneText;
    private EditText eMailText;
    private EditText passwordText;

    private String student, teacher;

    private Spinner accountTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        if (createAccountButton != null)
            createAccountButton.setOnClickListener(this);
    }

    private void initialize() {
        student = getResources().getString(R.string.student);
        teacher = getResources().getString(R.string.teacher);

        nameText = (EditText) findViewById(R.id.nameText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        eMailText = (EditText) findViewById(R.id.eMailText);
        passwordText = (EditText) findViewById(R.id.create_account_edittext_password);

        initializeSpinner();
    }

    private void initializeSpinner() {
        accountTypeSpinner = (Spinner) findViewById(R.id.create_account_spinner_account_type);
        String[] items = new String[]{student, teacher};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (accountTypeSpinner != null) {
            accountTypeSpinner.setAdapter(adapter);

            // Set default account type to Student.
            accountTypeSpinner.setSelection(0);
        }
    }

    public void createAccount() {
        String email = this.eMailText.getText().toString();
        String password = this.passwordText.getText().toString();

        User user = createNewUser(email);

        Account account = new Account(email, password);
        account.setUser(user);

        FireDatabase.getInstance().pushAccount(account);
        SessionData.setLoggedInUser(user);

        Toast.makeText(this, R.string.create_account_successful_toast, Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, StartActivity.class));
    }

    private User createNewUser(String email) {
        String name = this.nameText.getText().toString();
        String accountType = getSelectedAccounType();
        boolean makeStudent = student.equals(accountType);

        User user = makeStudent ? new StudentUser(name) : new TeacherUser(name);
        user.setPhoneNumber(getPhoneNumber());
        user.setEmail(email);
        user.saveEmailToLocalStorage(this);

        return user;
    }

    private String getPhoneNumber() {
        String phoneNumber = phoneText.getText().toString();
        return phoneNumber.isEmpty() ? "N/A" : phoneNumber;
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

    public String getSelectedAccounType() {
        return accountTypeSpinner.getSelectedItem().toString();
    }
}
