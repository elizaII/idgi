package com.idgi.activities;

import android.content.Intent;

import com.idgi.activities.extras.NameableListActivity;
import com.idgi.core.Nameable;
import com.idgi.core.Subject;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.idgi.application.Application;
import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.event.BusEvent;
import com.idgi.event.Event;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;

public class SubjectListActivity extends NameableListActivity {
    private List<Subject> subjects;
    private final EventBus bus = Application.getEventBus();

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
            bus.unregister(this);
        }
    }

    @Override
    public void onNameableSelected(Nameable nameable) {
        //Stupid git
    }
}
