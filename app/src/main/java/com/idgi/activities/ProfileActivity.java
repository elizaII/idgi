package com.idgi.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.idgi.R;
import com.idgi.core.User;
import com.idgi.util.AppCompatActivityWithDrawer;
import com.idgi.util.Navigation;

public class ProfileActivity extends AppCompatActivityWithDrawer {

    User user;
    EditText name;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userEmail = User.getLoggedInUserEmail(this);
       // user = Firebase(dfkdkgkgk: userEmail);

        name = (EditText) findViewById(R.id.profile_editText_name);
        age = (EditText) findViewById(R.id.profile_editText_age);

        initializeDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            if (age.getTag() == "enabled") {
                disableInputField(age);
            }
            else {
                enableInputField(age);
            }
        }
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

    private void saveInfo(View view) {
        user.setName(name.getText().toString());
        user.setAge(age.getText().toString());
    }
}
