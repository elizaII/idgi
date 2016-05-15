package com.idgi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;


public class ProfileActivity extends DrawerActivity {

    User user;
    EditText name;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userEmail = User.getLoggedInUserEmail(this);

        name = (EditText) findViewById(R.id.profile_editText_name);
        age = (EditText) findViewById(R.id.profile_editText_age);
        user = SessionData.getLoggedInUser();
       if (user != null) {
           name.setText(user.getName());
           age.setText(user.getAge());
           age.setText(user.getEmail());
       }
        initializeDrawer();
    }

    private void enableInputField(EditText field) {
        field.setFocusableInTouchMode(true);
        field.setClickable(true);
        field.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(field, InputMethodManager.SHOW_IMPLICIT);
        field.setTag("enabled");
    }
    private void disableInputField(EditText field) {
        field.setFocusableInTouchMode(false);
        field.setClickable(false);
        field.clearFocus();
        field.setTag("disabled");
    }

    private void saveInfo() {
        if (user != null) {
            user.setName(name.getText().toString());
            user.setAge(age.getText().toString());
        }
    }
    public void toggleEditState(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        if (checked == true) {
            enableInputField(age);
            enableInputField(name);
        }
        if (checked == false) {
            disableInputField(name);
            disableInputField(age);
            saveInfo();
        }
    }
    public void showMyHats(View view) {
        Intent intent = new Intent(this, HatListActivity.class);
        startActivity(intent);
    }
}
