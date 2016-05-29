package com.idgi.application;

import android.content.Context;
import android.net.ConnectivityManager;

import com.firebase.client.Firebase;
import com.idgi.service.FireDatabase;
import com.idgi.service.IDatabase;

public class Main extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
		FireDatabase.getInstance().initialize(connectedToInternet());
	}

	public static IDatabase getDatabase() {
		return FireDatabase.getInstance();
	}

	private boolean connectedToInternet() {
		ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		return connectivityManager != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
