package com.idgi.android.activity;

import android.content.Intent;

import com.idgi.core.Nameable;
import com.idgi.core.Subject;
import com.google.common.eventbus.Subscribe;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.List;

/*
Lists all subjects of a school
 */
public class SubjectListActivity extends NameableListActivity {

	@Override
	protected String getTitleName() {
		return SessionData.getCurrentSchool().getName();
	}

	@Override
	protected List<? extends Nameable> getNameables() {
		return SessionData.getCurrentSchool().getSubjects();
	}

    @Subscribe
    public void onSubjectSelected(BusEvent busEvent) {
        if(busEvent.getEvent() == Event.SUBJECT_SELECTED){
            Subject subject = (Subject) busEvent.getData();
            SessionData.setCurrentSubject(subject);

            startActivity(new Intent(this, CourseListActivity.class));
        }
    }
}
