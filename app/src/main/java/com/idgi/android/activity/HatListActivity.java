package com.idgi.android.activity;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.core.Student;
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

        if (user != null && user.getClass() == Student.class)
            return ((Student) user).getHats();
        else
            return new ArrayList<>();
    }
}
