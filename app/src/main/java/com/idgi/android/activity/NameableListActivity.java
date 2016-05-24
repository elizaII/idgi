package com.idgi.android.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.idgi.R;
import com.idgi.core.Nameable;
import com.idgi.android.recycleView.RecyclerViewUtility;
import com.idgi.android.recycleView.adapters.NameableAdapter;

import java.util.List;

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
}
