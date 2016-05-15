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
    EditText txtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userEmail = User.getLoggedInUserEmail(this);

        name = (EditText) findViewById(R.id.profile_editText_name);
        txtAge = (EditText) findViewById(R.id.profile_editText_age);
        user = SessionData.getLoggedInUser();
       if (user != null) {
           name.setText(user.getName());
           txtAge.setText(user.getAge());
           txtAge.setText(user.getEmail());
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
            // TODO check if it actually is parse-able. This is gonna crash if it is not an int
            user.setAge(Integer.parseInt(txtAge.getText().toString()));
        }
    }
    public void toggleEditState(View view) {
        boolean checked = ((ToggleButton)view).isChecked();
        if (checked) {
            enableInputField(txtAge);
            enableInputField(name);
        }
        if (!checked) {
            disableInputField(name);
            disableInputField(txtAge);
            saveInfo();
        }
    }

    public void showMyHats(View view) {
        Intent intent = new Intent(this, HatListActivity.class);
        startActivity(intent);
    }

    public void enableInputFieldButtonClicked(View view) {
        if (view.getId() == R.id.profile_btn_editName ) {
            if (name.getTag() == "enabled") {
                disableInputField(name);
            }
            else {
                enableInputField(name);
            }
        } else if (view.getId() == R.id.profile_btn_editAge) {
            if (txtAge.getTag() == "enabled") {
                disableInputField(txtAge);
            }
            else {
                enableInputField(txtAge);
            }
        }
    }
}
