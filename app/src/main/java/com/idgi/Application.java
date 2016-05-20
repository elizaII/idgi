package com.idgi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import com.firebase.client.Firebase;
import com.google.common.eventbus.EventBus;
import com.idgi.core.StudentUser;
import com.idgi.core.User;
import com.idgi.services.FireDatabase;
import com.idgi.session.SessionData;

public class Application extends android.app.Application {

	private static EventBus eventBus = null;

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
		//Firebase.getDefaultConfig().setPersistenceEnabled(true);
		FireDatabase.getInstance().initialize(connectedToInternet());
		//FireDatabase.getInstance().pushMockDataToFirebase();

	}

	public static synchronized EventBus getEventBus() {
		if(eventBus == null) {
			eventBus = new EventBus();
		}

		return eventBus;
	}

	private boolean connectedToInternet() {
		ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		return connectivityManager != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
