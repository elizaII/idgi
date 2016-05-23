package com.idgi.activities;

import android.content.Intent;

import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.Subject;
import com.idgi.session.SessionData;

import java.util.List;

public class SubjectListActivity extends NameableListActivity {

	@Override
	protected String getTitleName() {
		return SessionData.getCurrentSchool().getName();
	}

	@Override
	protected List<? extends Nameable> getNameables() {
		return SessionData.getCurrentSchool().getSubjects();
	}

	// TODO Implement listener using new EventBus
    public void onNameableSelected(Nameable nameable) {
        if(nameable instanceof Subject){
            Subject subject = (Subject) nameable;
            SessionData.setCurrentSubject(subject);

            startActivity(new Intent(this, CourseListActivity.class));
        }
    }
}
