package com.idgi.android.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.idgi.ImageUtility;
import com.idgi.R;
import com.idgi.application.Application;
import com.idgi.core.Account;
import com.idgi.core.Student;
import com.idgi.core.User;
import com.idgi.session.SessionData;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

/*
Displays general information of the logged in User.
 */
public class ProfileActivity extends DrawerActivity {
    EditText name;
    EditText txtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.profile_editText_name);
        txtAge = (EditText) findViewById(R.id.profile_editText_age);

        Account account = SessionData.getLoggedInAccount();

       if (account != null) {
           User user = SessionData.getLoggedInUser();

           if (user != null) {
               name.setText(user.getName());
               txtAge.setText(String.format(Locale.ENGLISH, "%d", user.getAge()));
               txtAge.setText(account.getEmail());
           }
       }

        initializeDrawer();

        initializeProfilePictureButton();

        initializeProfilePicture();

        handleIntent();
    }

    private void initializeProfilePicture() {
        ImageView imageView = (ImageView) findViewById(R.id.lesson_listitem_comment_image_profile_picture);

        User user = SessionData.getLoggedInUser();
        if (user != null && imageView != null)
            imageView.setImageBitmap(user.getProfilePicture());
    }

    private void initializeProfilePictureButton() {
        Button button = (Button) findViewById(R.id.profile_btn_change_picture);
        if (button != null)
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent galleryIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(galleryIntent);
                }
            });
    }

    private void handleIntent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null && type.startsWith("image/")) {
            Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (imageUri != null) {
                if (SessionData.hasLoggedInUser()) {
                    User user = SessionData.getLoggedInUser();
                    if (user != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            user.setProfilePicture(
                                    ImageUtility.drawableToBitmap(Drawable.createFromStream(
                                            inputStream, imageUri.toString())));
                            Application.getDatabase().saveProfilePicture(SessionData.getLoggedInAccount());
                        } catch (FileNotFoundException e) {
                            user.setProfilePicture(
                                    ImageUtility.drawableToBitmap(ContextCompat.
                                            getDrawable(this, R.drawable.ic_account_circle_black_24dp)));
                            Application.getDatabase().saveProfilePicture(SessionData.getLoggedInAccount());
                        }
                    }
                }
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

    private void saveInfo() {
        User user = SessionData.getLoggedInUser();
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
}
