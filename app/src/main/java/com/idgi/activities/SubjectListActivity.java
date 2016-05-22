package com.idgi.activities;

import android.content.Intent;

import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.Subject;
import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.event.ApplicationBus;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.session.SessionData;

import java.util.List;

public class SubjectListActivity extends NameableListActivity {
    private List<Subject> subjects;

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
