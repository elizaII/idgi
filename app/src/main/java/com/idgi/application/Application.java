package com.idgi.application;

import android.content.Context;
import android.net.ConnectivityManager;

import com.firebase.client.Firebase;
import com.idgi.core.StudentUser;
import com.idgi.core.TeacherUser;
import com.idgi.core.User;
import com.idgi.services.FireDatabase;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
		//Firebase.getDefaultConfig().setPersistenceEnabled(true);
		FireDatabase.getInstance().initialize(connectedToInternet());
		//FireDatabase.getInstance().pushMockDataToFirebase();
//		SessionData.setLoggedInUser(new StudentUser("Albus Dumbledore"));

	}

	private boolean connectedToInternet() {
		ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		return connectivityManager != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
