package com.idgi;

import android.os.Build;

import com.firebase.client.Firebase;
import com.idgi.core.StudentUser;
import com.idgi.core.User;
import com.idgi.services.FireDatabase;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
		//Firebase.getDefaultConfig().setPersistenceEnabled(true);
		FireDatabase.getInstance().initialize();
		//FireDatabase.getInstance().pushMockDataToFirebase();
	}

	User tove = new StudentUser("Tove Ekman");
	tove.addPoints(1000);
	SessionData.setCurrentUser(tove)
}
