package com.idgi.activities;

import com.idgi.R;
import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.StudentUser;
import com.idgi.core.User;
import com.idgi.session.SessionData;

import java.util.ArrayList;
import java.util.List;

public class HatListActivity extends NameableListActivity {

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.title_activity_my_hats);
    }

    @Override
    protected List<? extends Nameable> getNameables() {
        User user = SessionData.getLoggedInUser();

        if (user != null && user.getClass() == StudentUser.class)
            return ((StudentUser) user).getHats();
        else
            return new ArrayList<>();
    }

    @Override
    public void onNameableSelected(Nameable nameable) {
        return;
    }
}
