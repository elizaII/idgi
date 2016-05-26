package com.idgi.android.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewUtility {

	// Connects an adapter to a RecyclerView and gives it a default LayoutManager
	public static void connect(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
		if (recyclerView != null) {
			recyclerView.setAdapter(adapter);
			recyclerView.setLayoutManager(new LinearLayoutManager(context));
		}
	}
}
