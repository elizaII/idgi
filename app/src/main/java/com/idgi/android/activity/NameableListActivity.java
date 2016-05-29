package com.idgi.android.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.common.eventbus.Subscribe;
import com.idgi.R;
import com.idgi.android.dialog.LoginRequiredDialog;
import com.idgi.core.Nameable;
import com.idgi.android.recyclerview.RecyclerViewUtility;
import com.idgi.android.recyclerview.adapters.NameableAdapter;
import com.idgi.event.BusEvent;

import java.util.List;

/*
Activities that only contain a list of Nameables extend this class.
Note: getTitleName is not named getTitle due to superclass declaring a getTitle() as final.
 */
public abstract class NameableListActivity extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
		initializeNameableList();

        super.initializeWithTitle(getTitleName());
    }

	@Override
	protected void onResume() {
		super.onResume();
	}

	protected int getLayout() {
		return R.layout.activity_list_nameable;
	}

	protected abstract String getTitleName();
	protected abstract List<? extends Nameable> getNameables();

	private void initializeNameableList() {
		RecyclerView recycler = (RecyclerView) findViewById(R.id.activity_list_nameable_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, getNameables());
		RecyclerViewUtility.connect(this, recycler, adapter);
	}

	@Subscribe
	public void onLoginRequiredEvent(BusEvent busEvent) {
		switch (busEvent.getEvent()) {
			case LOGIN_REQUIRED_DIALOG:
				new LoginRequiredDialog(this).show();
				break;
			default:
		}
	}
}
