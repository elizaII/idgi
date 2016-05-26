package com.idgi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.idgi.R;
import com.idgi.core.Account;
import com.idgi.core.Student;
import com.idgi.core.Teacher;
import com.idgi.core.User;
import com.idgi.service.FireDatabase;
import com.idgi.session.SessionData;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String ACCOUNT_TYPE_STUDENT = "Student";
    private static final String ACCOUNT_TYPE_TEACHER = "Lärare";

    private EditText nameText;
    private EditText phoneText;
    private EditText eMailText;
    private EditText passwordText;
    private String selectedAccountType;

/*
    private EditText firstName = (EditText)findViewById(R.id.create_account_edittext_name);
    if (firstName
            getText().toString().length() == 0 )
            firstName.setError( "First name is required!" );
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameText = (EditText) findViewById(R.id.create_account_edittext_name);
        phoneText = (EditText) findViewById(R.id.create_account_edittext_phoneText);
        eMailText = (EditText) findViewById(R.id.create_account_edittext_email);
        passwordText = (EditText) findViewById(R.id.create_account_edittext_password);

        Spinner accountTypeSpinner = (Spinner) findViewById(R.id.create_account_spinner_account_type);
        String[] items = new String[]{ACCOUNT_TYPE_STUDENT, ACCOUNT_TYPE_TEACHER};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (accountTypeSpinner != null) {
            accountTypeSpinner.setAdapter(adapter);
            accountTypeSpinner.setOnItemSelectedListener(this);

            // Set default account type to Student.
            accountTypeSpinner.setSelection(0);
            selectedAccountType = ACCOUNT_TYPE_STUDENT;
        }

        Button createAccountButton = (Button) findViewById(R.id.create_account_btn_create_account);
        if (createAccountButton != null) {
            createAccountButton.setOnClickListener(this);
        }
    }

    public void createAccount() {
        String name = this.nameText.getText().toString();
        String email = this.eMailText.getText().toString();
        String password = this.passwordText.getText().toString();

        Account account = new Account(email, password);

        User user;
        if (selectedAccountType.equals(ACCOUNT_TYPE_STUDENT)) {
            user = new Student(name);
        } else {
            user = new Teacher(name);
        }
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

        FireDatabase.getInstance().pushAccount(account);
        SessionData.setLoggedInAccount(account);

        Toast.makeText(this, R.string.create_account_successful_toast, Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, StartActivity.class));
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
        if (v.getId() == R.id.create_account_btn_create_account) {
            if (formIsValid()) {
                createAccount();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedAccountType = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing.
    }
}
