package com.idgi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.core.ModelUtility;
import com.idgi.core.Nameable;
import com.idgi.core.Subject;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;
import com.idgi.session.SessionData;

import java.util.List;

public class SubjectListActivity extends DrawerActivity implements NameableSelectionBus.Listener{
    private List<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO Solve this in a better way instead of just going back to the previous activity.
        if (SessionData.getCurrentSchool() == null) {
            System.out.println(":::::::NOTE:::::: Can not enter SubjectListActivity because Storage.getCurrentSchool() returns null");
            finish();
        }

        setContentView(R.layout.activity_subject_list);

		initializeSubjectList();

        super.initializeWithTitle(SessionData.getCurrentSchool().getName());

    }


	private void initializeSubjectList() {
        subjects = SessionData.getCurrentSchool().getSubjects();

		RecyclerView recycler = (RecyclerView) findViewById(R.id.subject_list_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, subjects);
        adapter.addListener(this);

		RecyclerViewUtility.connect(this, recycler, adapter);
	}

    @Override
    public void onNameableSelected(Nameable nameable) {
        if(nameable instanceof Subject){
            Subject subject = (Subject) nameable;
            SessionData.setCurrentSubject(subject);

            startActivity(new Intent(this, CourseListActivity.class));
        }
    }
}
