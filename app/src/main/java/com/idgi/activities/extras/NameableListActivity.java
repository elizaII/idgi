package com.idgi.activities.extras;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.activities.extras.DrawerActivity;
import com.idgi.core.Nameable;
import com.idgi.event.NameableSelectionBus;
import com.idgi.recycleViews.RecyclerViewUtility;
import com.idgi.recycleViews.adapters.NameableAdapter;

import java.util.List;

public abstract class NameableListActivity extends DrawerActivity implements NameableSelectionBus.Listener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
		initializeNameableList();

        super.initializeWithTitle(getTitleName());
    }

	protected int getLayout() {
		return R.layout.activity_list_nameable;
	}

	protected abstract String getTitleName();
	protected abstract List<? extends Nameable> getNameables();
	public abstract void onNameableSelected(Nameable nameable);


	private void initializeNameableList() {
		RecyclerView recycler = (RecyclerView) findViewById(R.id.activity_list_nameable_recycler_view);
		NameableAdapter adapter = new NameableAdapter(this, getNameables());
		adapter.addListener(this);
		RecyclerViewUtility.connect(this, recycler, adapter);
	}
}
