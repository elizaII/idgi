package com.idgi.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.idgi.R;
import com.idgi.core.User;
import com.idgi.core.StudentUser;
import com.idgi.android.activities.extras.DrawerActivity;
import com.idgi.session.SessionData;

import java.util.Locale;


public class ProfileActivity extends DrawerActivity {

    User user;
    EditText name;
    EditText txtAge;
    TextView points;

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
           txtAge.setText(String.format(Locale.ENGLISH, "%d", user.getAge()));
           txtAge.setText(user.getEmail());

//      if (user.getType() == User.Type.Student) {
           if (user instanceof StudentUser) {
               points = (TextView) findViewById(R.id.profile_textView_nrOfPoints);
               points.setText(String.valueOf(((StudentUser) user).getPoints()));
           }
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
            String ageText = txtAge.getText().toString();

            if (canBeParsedToInt(ageText))
            user.setAge(Integer.parseInt(ageText));
        }
    }

    private boolean canBeParsedToInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
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

    public void showMyPoints(User user) {
        user = SessionData.getLoggedInUser();
        if (user instanceof StudentUser) {
            ((StudentUser) user).getPoints();
        }
    }
}
